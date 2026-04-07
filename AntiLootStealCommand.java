package com.antilootsteal.commands;

import com.antilootsteal.AntiLootSteal;
import com.antilootsteal.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AntiLootStealCommand implements CommandExecutor {

    private final AntiLootSteal plugin;

    public AntiLootStealCommand(AntiLootSteal plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("antilootsteal.admin")) {
            sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMsgNoPermission()));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MessageUtil.colorize("&6&lAntiLootSteal &7v" + plugin.getDescription().getVersion()));
            sender.sendMessage(MessageUtil.colorize("&e/antilootsteal reload &7- Reload config"));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.getConfigManager().reload();
            plugin.getHologramManager().stopUpdateTask();
            plugin.getHologramManager().startUpdateTask();
            sender.sendMessage(MessageUtil.colorize(plugin.getConfigManager().getMsgReloadSuccess()));
            return true;
        }

        sender.sendMessage(MessageUtil.colorize("&cUnknown subcommand. Use &e/antilootsteal reload"));
        return true;
    }
}
