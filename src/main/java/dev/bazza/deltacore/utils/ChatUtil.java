package dev.bazza.deltacore.utils;

import dev.bazza.deltacore.data.DeltaPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public class ChatUtil {

    public static TextComponent AFK_NOTIFY(boolean isAfk) {
        return (isAfk) ? AFK_ON_NOTIFY() : AFK_OFF_NOTIFY();
    }

    public static TextComponent AFK_ON_NOTIFY() {
        return Component.text()
                .content("You are now AFK.")
                .color(NamedTextColor.GRAY)
                .build();
    }

    public static TextComponent AFK_OFF_NOTIFY() {
        return Component.text()
                .content("You are no longer AFK.")
                .color(NamedTextColor.GRAY)
                .build();
    }

    public static TextComponent AFK_NOTIFY_ALL(boolean isAfk, DeltaPlayer player) {
        String displayName = player.getDisplayName();
        return (isAfk) ? AFK_ON_NOTIFY_ALL(displayName) : AFK_OFF_NOTIFY_ALL(displayName);
    }

    public static TextComponent AFK_ON_NOTIFY_ALL(String displayName) {
        return Component.text()
                .content(displayName).color(NamedTextColor.GRAY)
                .append(Component.text().content(" is now AFK.").color(NamedTextColor.GRAY))
                .build();
    }

    public static TextComponent AFK_OFF_NOTIFY_ALL(String displayName) {
        return Component.text()
                .content(displayName).color(NamedTextColor.GRAY)
                .append(Component.text().content(" is no longer AFK.").color(NamedTextColor.GRAY))
                .build();
    }

    public static String CONSOLE_CANNOT_EXECUTE() {
       return ColorUtil.color("&cOnly players can use this command.");
    }


}
