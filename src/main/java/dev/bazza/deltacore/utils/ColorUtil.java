package dev.bazza.deltacore.utils;

import net.md_5.bungee.api.ChatColor;

public class ColorUtil {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
