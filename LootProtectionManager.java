package com.antilootsteal.managers;

import com.antilootsteal.AntiLootSteal;
import com.antilootsteal.config.ConfigManager;
import com.antilootsteal.models.ProtectedLoot;
import com.antilootsteal.utils.MessageUtil;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LootProtectionManager {

    private final AntiLootSteal plugin;
    private final Map<UUID, ProtectedLoot> protectedItems = new ConcurrentHashMap<>();
    private final Map<UUID, Long> messageCooldowns = new HashMap<>();

    private static final long MESSAGE_COOLDOWN_MS = 2000; // 2 seconds between messages

    public LootProtectionManager(AntiLootSteal plugin) {
        this.plugin = plugin;
    }

    public void protectDrops(Player killer, List<Item> items) {
        ConfigManager config = plugin.getConfigManager();
        HologramManager hologramManager = plugin.getHologramManager();
        long expirationTime = System.currentTimeMillis() + (config.getProtectionDuration() * 1000L);

        for (Item item : items) {
            ProtectedLoot loot = new ProtectedLoot(
                    killer.getUniqueId(),
                    killer.getName(),
                    item,
                    expirationTime
            );

            // Spawn hologram if enabled
            if (config.isHologramEnabled()) {
                org.bukkit.entity.ArmorStand hologram = hologramManager.spawnHologram(
                        item, killer.getName(), config.getProtectionDuration()
                );
                loot.setHologram(hologram);
            }

            // Schedule expiration
            final UUID itemUUID = item.getUniqueId();
            BukkitTask task = new BukkitRunnable() {
                @Override
                public void run() {
                    unprotect(itemUUID);
                }
            }.runTaskLater(plugin, config.getProtectionDuration() * 20L);
            loot.setTimerTask(task);

            protectedItems.put(itemUUID, loot);
        }
    }

    public boolean canPickup(Player player, Item item) {
        UUID itemUUID = item.getUniqueId();
        ProtectedLoot loot = protectedItems.get(itemUUID);

        if (loot == null) {
            return true; // Not protected
        }

        if (loot.isExpired()) {
            unprotect(itemUUID);
            return true;
        }

        // Owner can always pick up
        if (player.getUniqueId().equals(loot.getOwnerUUID())) {
            return true;
        }

        // Bypass permission
        if (player.hasPermission("antilootsteal.bypass")) {
            return true;
        }

        // Send denial message with cooldown
        sendDenialMessage(player, loot);
        return false;
    }

    private void sendDenialMessage(Player player, ProtectedLoot loot) {
        long now = System.currentTimeMillis();
        Long lastMsg = messageCooldowns.get(player.getUniqueId());
        if (lastMsg != null && (now - lastMsg) < MESSAGE_COOLDOWN_MS) {
            return;
        }
        messageCooldowns.put(player.getUniqueId(), now);

        ConfigManager config = plugin.getConfigManager();
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{player}", loot.getOwnerName());
        placeholders.put("{time}", String.valueOf(loot.getRemainingSeconds()));

        player.sendMessage(MessageUtil.replacePlaceholders(config.getMsgLootProtected(), placeholders));
    }

    public void unprotect(UUID itemEntityUUID) {
        ProtectedLoot loot = protectedItems.remove(itemEntityUUID);
        if (loot != null) {
            loot.cleanup();
        }
    }

    public boolean isProtected(Item item) {
        return protectedItems.containsKey(item.getUniqueId());
    }

    public Map<UUID, ProtectedLoot> getProtectedItems() {
        return protectedItems;
    }

    public void cleanup() {
        for (ProtectedLoot loot : protectedItems.values()) {
            loot.cleanup();
        }
        protectedItems.clear();
        messageCooldowns.clear();
    }
}
