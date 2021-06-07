package dev.bazza.deltacore.utils;

public class CommandUtil {

    public static String argsToString(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String word : args) {
            stringBuilder.append(word).append(" ");
        }

        return stringBuilder.toString().trim();
    }
}
