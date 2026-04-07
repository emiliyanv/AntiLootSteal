package com.antilootsteal.config;

import com.antilootsteal.AntiLootSteal;
import com.antilootsteal.utils.MessageUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

public class ConfigManager {

    private final AntiLootSteal plugin;

    private int protectionDuration;
    private Set<String> enabledWorlds;
    private Set<String> disabledWorlds;
    private boolean protectPlayers;
    private boolean protectZombies;
    private boolean protectAnimals;

    private boolean hologramEnabled;
    private double hologramYOffset;
    private String colorHigh;
    private String colorMedium;
    private String colorLow;
    private int mediumThreshold;
    private int lowThreshold;
    private String hologramFormat;
    private int updateInterval;

    private String msgLootProtected;
    private String msgLootExpired;
    private String msgReloadSuccess;
    private String msgNoPermission;

    public ConfigManager(AntiLootSteal plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();

        protectionDuration = config.getInt("protection-duration", 15);

        enabledWorlds = new HashSet<>(config.getStringList("enabled-worlds"));
        disabledWorlds = new HashSet<>(config.getStringList("disabled-worlds"));

        protectPlayers = config.getBoolean("protected-entities.players", true);
        protectZombies = config.getBoolean("protected-entities.zombies", true);
        protectAnimals = config.getBoolean("protected-entities.animals", true);

        hologramEnabled = config.getBoolean("hologram.enabled", true);
        hologramYOffset = config.getDouble("hologram.y-offset", 1.2);
        colorHigh = config.getString("hologram.color-high", "&a");
        colorMedium = config.getString("hologram.color-medium", "&e");
        colorLow = config.getString("hologram.color-low", "&c");
        mediumThreshold = config.getInt("hologram.medium-threshold", 10);
        lowThreshold = config.getInt("hologram.low-threshold", 5);
        hologramFormat = config.getString("hologram.format", "{color}&l{time}s &7| &f{player}'s loot");
        updateInterval = config.getInt("hologram.update-interval", 20);

        msgLootProtected = config.getString("messages.loot-protected",
                "&c&lAntiLootSteal &8> &7This loot belongs to &e{player}&7! Wait &e{time}s&7.");
        msgLootExpired = config.getString("messages.loot-expired",
                "&a&lAntiLootSteal &8> &7Loot protection has expired. Items are now free.");
        msgReloadSuccess = config.getString("messages.reload-success",
                "&a&lAntiLootSteal &8> &7Config reloaded successfully.");
        msgNoPermission = config.getString("messages.no-permission",
                "&c&lAntiLootSteal &8> &7You don't have permission to do that.");
    }

    public void reload() {
        plugin.reloadConfig();
        load();
    }

    public boolean isWorldEnabled(String worldName) {
        if (!disabledWorlds.isEmpty() && disabledWorlds.contains(worldName)) {
            return false;
        }
        if (!enabledWorlds.isEmpty()) {
            return enabledWorlds.contains(worldName);
        }
        return true;
    }

    public int getProtectionDuration() { return protectionDuration; }
    public boolean isProtectPlayers() { return protectPlayers; }
    public boolean isProtectZombies() { return protectZombies; }
    public boolean isProtectAnimals() { return protectAnimals; }
    public boolean isHologramEnabled() { return hologramEnabled; }
    public double getHologramYOffset() { return hologramYOffset; }
    public String getColorHigh() { return colorHigh; }
    public String getColorMedium() { return colorMedium; }
    public String getColorLow() { return colorLow; }
    public int getMediumThreshold() { return mediumThreshold; }
    public int getLowThreshold() { return lowThreshold; }
    public String getHologramFormat() { return hologramFormat; }
    public int getUpdateInterval() { return updateInterval; }
    public String getMsgLootProtected() { return msgLootProtected; }
    public String getMsgLootExpired() { return msgLootExpired; }
    public String getMsgReloadSuccess() { return msgReloadSuccess; }
    public String getMsgNoPermission() { return msgNoPermission; }
}
