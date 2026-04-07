package com.antilootsteal;

import com.antilootsteal.commands.AntiLootStealCommand;
import com.antilootsteal.config.ConfigManager;
import com.antilootsteal.listeners.EntityDeathListener;
import com.antilootsteal.listeners.ItemMergeListener;
import com.antilootsteal.listeners.ItemPickupListener;
import com.antilootsteal.managers.HologramManager;
import com.antilootsteal.managers.LootProtectionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiLootSteal extends JavaPlugin {

    private static AntiLootSteal instance;
    private ConfigManager configManager;
    private LootProtectionManager lootProtectionManager;
    private HologramManager hologramManager;

    @Override
    public void onEnable() {
        instance = this;

        // Initialize managers
        configManager = new ConfigManager(this);
        hologramManager = new HologramManager(this);
        lootProtectionManager = new LootProtectionManager(this);

        // Register listeners
        getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new ItemPickupListener(this), this);

        // Register ItemMergeEvent listener (available since 1.8)
        try {
            Class.forName("org.bukkit.event.entity.ItemMergeEvent");
            getServer().getPluginManager().registerEvents(new ItemMergeListener(this), this);
        } catch (ClassNotFoundException ignored) {
            // ItemMergeEvent not available on this version
        }

        // Register command
        getCommand("antilootsteal").setExecutor(new AntiLootStealCommand(this));

        // Start hologram update task
        if (configManager.isHologramEnabled()) {
            hologramManager.startUpdateTask();
        }

        getLogger().info("AntiLootSteal v" + getDescription().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        // Clean up all protected loot and holograms
        if (lootProtectionManager != null) {
            lootProtectionManager.cleanup();
        }
        if (hologramManager != null) {
            hologramManager.stopUpdateTask();
        }

        getLogger().info("AntiLootSteal disabled.");
        instance = null;
    }

    public static AntiLootSteal getInstance() { return instance; }
    public ConfigManager getConfigManager() { return configManager; }
    public LootProtectionManager getLootProtectionManager() { return lootProtectionManager; }
    public HologramManager getHologramManager() { return hologramManager; }
}
