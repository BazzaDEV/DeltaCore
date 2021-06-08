package dev.bazza.deltacore.utils;

import org.bukkit.command.CommandSender;

public class Util {

    public static void msg(CommandSender sender, String msg) {
        sender.sendMessage(ColorUtil.color(msg));
    }
}
