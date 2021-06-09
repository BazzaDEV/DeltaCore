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

    public static void println(Object... args) {
        for (Object o : args) {
            String s = (o instanceof String) ? ( (String) o ) : ( o.toString() );
            System.out.println(s);
        }
    }
}
