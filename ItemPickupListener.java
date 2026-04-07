package com.antilootsteal.listeners;

import com.antilootsteal.AntiLootSteal;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

@SuppressWarnings("deprecation")
public class ItemPickupListener implements Listener {

    private final AntiLootSteal plugin;

    public ItemPickupListener(AntiLootSteal plugin) {
        this.plugin = plugin;
    }

    // Using PlayerPickupItemEvent (deprecated but works on ALL versions 1.8+)
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();

        if (!plugin.getLootProtectionManager().canPickup(player, item)) {
            event.setCancelled(true);
        }
    }
}
