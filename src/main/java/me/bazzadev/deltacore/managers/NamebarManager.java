package me.bazzadev.deltacore.managers;

import com.nametagedit.plugin.NametagEdit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NamebarManager {

    public void updatePrefix(Player player) {

        StringBuilder newPrefix = new StringBuilder();
        boolean empty = true;

        if (AFKManager.getStatus(player)) {
            newPrefix.append("&8[&7AFK&8] &7");
            NametagEdit.getApi().setPrefix(player, newPrefix.toString());
            return;
        }

        if (StaffModeManager.getStatus(player)) {
            newPrefix.append("&8[&bStaff&8]&r ");
            empty = false;
        }

        if (VanishManager.isVanished(player)) {
            newPrefix.append("&8[&o&dVanish&8]&r ");
            empty = false;
        }

        if (empty) {
            NametagEdit.getApi().setPrefix(player, "");

        } else {
            NametagEdit.getApi().setPrefix(player, newPrefix.toString());

        }

    }

    public void updateAll() {

        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePrefix(p);
        }

    }

}
