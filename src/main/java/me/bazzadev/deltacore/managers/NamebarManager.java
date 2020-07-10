package me.bazzadev.deltacore.managers;

import com.nametagedit.plugin.NametagEdit;
import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NamebarManager {

    private final PlayerUtil playerUtil;

    public NamebarManager(PlayerUtil playerUtil) {
        this.playerUtil = playerUtil;
    }


    public void update(Player player) {

        StringBuilder newPrefix = new StringBuilder();

        if (playerUtil.isAFK(player)) {
            newPrefix.append("&8[&7AFK&8] &7");
            DeltaCore.newChain()
                    .sync(() -> NametagEdit.getApi().setPrefix(player, newPrefix.toString()))
                    .execute();


        } else {
            boolean empty = true;
            if (playerUtil.isStaffMode(player)) {
                newPrefix.append("&8[&bSM&8]&r ");
                empty = false;
            }
            if (playerUtil.isVanished(player)) {
                newPrefix.append("&8[&o&dV&8]&r ");
                empty = false;
            }

            if (empty) {
                DeltaCore.newChain()
                        .sync(() -> NametagEdit.getApi().setPrefix(player, ""))
                        .execute();

            } else {
                DeltaCore.newChain()
                        .sync(() -> NametagEdit.getApi().setPrefix(player, newPrefix.toString()))
                        .execute();

            }

        }

    }

    public void updateAll() {

        for (Player p : Bukkit.getOnlinePlayers()) {
            update(p);
        }

    }

}
