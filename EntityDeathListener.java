package com.antilootsteal.listeners;

import com.antilootsteal.AntiLootSteal;
import com.antilootsteal.config.ConfigManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class EntityDeathListener implements Listener {

    private final AntiLootSteal plugin;

    public EntityDeathListener(AntiLootSteal plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();

        // Must be killed by a player
        if (killer == null) return;

        // Killer must have protect permission
        if (!killer.hasPermission("antilootsteal.protect")) return;

        ConfigManager config = plugin.getConfigManager();

        // Check world
        if (!config.isWorldEnabled(entity.getWorld().getName())) return;

        // Check entity type
        if (!isProtectedEntityType(entity, config)) return;

        // Capture drops and clear them from the event
        List<ItemStack> drops = new ArrayList<>(event.getDrops());
        event.getDrops().clear();

        if (drops.isEmpty()) return;

        // Store death location and world
        final Location deathLocation = entity.getLocation().clone();
        final World world = entity.getWorld();
        final Player theKiller = killer;

        // Spawn items manually 1 tick later and protect them
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Item> spawnedItems = new ArrayList<>();
                for (ItemStack stack : drops) {
                    if (stack == null || stack.getType().name().equals("AIR")) continue;
                    Item droppedItem = world.dropItemNaturally(deathLocation, stack);
                    spawnedItems.add(droppedItem);
                }

                if (!spawnedItems.isEmpty()) {
                    plugin.getLootProtectionManager().protectDrops(theKiller, spawnedItems);
                }
            }
        }.runTaskLater(plugin, 1L);
    }

    private boolean isProtectedEntityType(LivingEntity entity, ConfigManager config) {
        // Player
        if (entity instanceof Player) {
            return config.isProtectPlayers();
        }

        // Zombies (Zombie, ZombieVillager, Husk, Drowned, etc.)
        if (entity instanceof Zombie) {
            return config.isProtectZombies();
        }

        // Animals (all passive mobs extend Animals)
        if (entity instanceof Animals) {
            return config.isProtectAnimals();
        }

        return false;
    }
}
