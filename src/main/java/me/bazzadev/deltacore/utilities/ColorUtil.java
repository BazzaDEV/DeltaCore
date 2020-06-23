package me.bazzadev.deltacore.utilities;

import me.bazzadev.deltacore.afk.AFKManager;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class ColorUtil {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String coloredAFKStatus(Player player) {
        if (AFKManager.getStatus(player)) {
            return ColorUtil.translate("&a&l✔");
        }
        return ColorUtil.translate("&c&l✘");
    }

    public static String coloredWorld(Player player) {
        World.Environment env = player.getWorld().getEnvironment();
        if (env==World.Environment.NORMAL) {
            return ColorUtil.translate("&2Overworld");
        } else if (env==World.Environment.NETHER) {
            return ColorUtil.translate("&4Nether");
        } else {
            return ColorUtil.translate("&5The End");
        }
    }
}
