package com.antilootsteal.managers;

import com.antilootsteal.AntiLootSteal;
import com.antilootsteal.config.ConfigManager;
import com.antilootsteal.models.ProtectedLoot;
import com.antilootsteal.utils.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramManager {

    private final AntiLootSteal plugin;
    private BukkitTask updateTask;

    public HologramManager(AntiLootSteal plugin) {
        this.plugin = plugin;
    }

    public ArmorStand spawnHologram(Item item, String ownerName, int remainingSeconds) {
        ConfigManager config = plugin.getConfigManager();
        Location loc = item.getLocation().clone().add(0, config.getHologramYOffset(), 0);

        ArmorStand stand = (ArmorStand) item.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setSmall(true);
        stand.setCustomNameVisible(true);
        stand.setCustomName(formatHologramText(ownerName, remainingSeconds));
        stand.setRemoveWhenFarAway(false);

        // setMarker(true) - available since 1.8.8
        try {
            stand.setMarker(true);
        } catch (NoSuchMethodError ignored) {
        }

        // setInvulnerable - available since 1.9, use reflection for 1.8 compatibility
        try {
            java.lang.reflect.Method setInvulnerable = stand.getClass().getMethod("setInvulnerable", boolean.class);
            setInvulnerable.invoke(stand, true);
        } catch (Exception ignored) {
        }

        // Prevent armor stand from appearing in tab or being targeted
        stand.setCanPickupItems(false);

        return stand;
    }

    public void removeHologram(ArmorStand stand) {
        if (stand != null && stand.isValid()) {
            stand.remove();
        }
    }

    public void startUpdateTask() {
        stopUpdateTask();
        ConfigManager config = plugin.getConfigManager();
        LootProtectionManager lootManager = plugin.getLootProtectionManager();

        updateTask = new BukkitRunnable() {
            @Override
            public void run() {
                Map<UUID, ProtectedLoot> protectedItems = lootManager.getProtectedItems();
                java.util.List<UUID> toRemove = new java.util.ArrayList<>();

                for (Map.Entry<UUID, ProtectedLoot> entry : protectedItems.entrySet()) {
                    ProtectedLoot loot = entry.getValue();
                    Item item = loot.getItemEntity();
                    ArmorStand hologram = loot.getHologram();

                    // Clean up if item is no longer valid
                    if (item == null || !item.isValid()) {
                        toRemove.add(entry.getKey());
                        continue;
                    }

                    if (hologram == null || !hologram.isValid()) {
                        continue;
                    }

                    // Update hologram position to follow item
                    Location newLoc = item.getLocation().clone().add(0, config.getHologramYOffset(), 0);
                    hologram.teleport(newLoc);

                    // Update text with remaining time
                    int remaining = loot.getRemainingSeconds();
                    hologram.setCustomName(formatHologramText(loot.getOwnerName(), remaining));
                }

                // Remove invalid entries
                for (UUID uuid : toRemove) {
                    lootManager.unprotect(uuid);
                }
            }
        }.runTaskTimer(plugin, 0L, config.getUpdateInterval());
    }

    public void stopUpdateTask() {
        if (updateTask != null) {
            updateTask.cancel();
            updateTask = null;
        }
    }

    public String formatHologramText(String ownerName, int remainingSeconds) {
        ConfigManager config = plugin.getConfigManager();

        String color;
        if (remainingSeconds <= config.getLowThreshold()) {
            color = config.getColorLow();
        } else if (remainingSeconds <= config.getMediumThreshold()) {
            color = config.getColorMedium();
        } else {
            color = config.getColorHigh();
        }

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{color}", color);
        placeholders.put("{time}", String.valueOf(remainingSeconds));
        placeholders.put("{player}", ownerName);

        return MessageUtil.replacePlaceholders(config.getHologramFormat(), placeholders);
    }
}
