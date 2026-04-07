package com.antilootsteal.listeners;

import com.antilootsteal.AntiLootSteal;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;

public class ItemMergeListener implements Listener {

    private final AntiLootSteal plugin;

    public ItemMergeListener(AntiLootSteal plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemMerge(ItemMergeEvent event) {
        Item item = event.getEntity();
        Item target = event.getTarget();

        // Prevent merging if either item is protected
        if (plugin.getLootProtectionManager().isProtected(item) ||
                plugin.getLootProtectionManager().isProtected(target)) {
            event.setCancelled(true);
        }
    }
}
