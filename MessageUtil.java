package com.antilootsteal.utils;

import org.bukkit.ChatColor;

import java.util.Map;

public class MessageUtil {

    public static String colorize(String input) {
        if (input == null) return "";
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String replacePlaceholders(String message, Map<String, String> placeholders) {
        if (message == null) return "";
        String result = message;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return colorize(result);
    }
}
