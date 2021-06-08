package dev.bazza.deltacore.utils;

import org.bukkit.command.CommandSender;

public class Util {

    public static void msg(CommandSender sender, String msg) {
        sender.sendMessage(ColorUtil.color(msg));
    }

    public static String argsToString(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String word : args) {
            stringBuilder.append(word).append(" ");
        }

        return stringBuilder.toString().trim();
    }
}
