package net.hexcap.minecraft.module.authme.service.logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Logger {
    private final char logo = '\u2B21';
    private final String prefix = translateColorCodes("&e" + logo + " &6Hexauth &r");

    public void info(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + translateColorCodes("&a" + message));
    }

    public void error(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + translateColorCodes("&c" + message));
    }

    public void warn(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + translateColorCodes("&e" + message));
    }

    public void info(String message, Player player) {
        player.sendMessage(prefix + translateColorCodes("&a" + message));
    }

    public void error(String message, Player player) {
        player.sendMessage(prefix + translateColorCodes("&c" + message));
    }

    public void warn(String message, Player player) {
        player.sendMessage(prefix + translateColorCodes("&e" + message));
    }

    public void debug(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + translateColorCodes("&b" + message));
    }

    private String translateColorCodes(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
