package me.bazzadev.deltacore.utilities;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
