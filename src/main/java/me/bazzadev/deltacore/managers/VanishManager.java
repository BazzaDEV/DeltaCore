package me.bazzadev.deltacore.managers;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class VanishManager {

    private final DeltaCore plugin;
    private final PlayerDataManager playerDataManager;
    private final PlayerUtil playerUtil;

    public VanishManager(DeltaCore plugin, PlayerDataManager playerDataManager, PlayerUtil playerUtil) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.playerUtil = playerUtil;
    }

    public void toggle(Player player) {

        if (playerUtil.isVanished(player)) {
            // Player is VANISHED. Toggle status to VISIBLE.
            unvanish(player);
            player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You are now &a&lVISIBLE &7to all players."));

        } else {
            // Player is VISIBLE. Toggle status to VANISHED.
            vanish(player);
            player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been &d&lVANISHED &7from all players."));

        }

    }

    private void vanish(Player player) {

        UUID uuid = player.getUniqueId();

        playerDataManager.getVanishMap().put(uuid, true);
        hidePlayer(player);

    }

    private void unvanish(Player player) {

        UUID uuid = player.getUniqueId();

        playerDataManager.getVanishMap().put(uuid, false);
        showPlayer(player);

    }

    public void hidePlayer(Player player) {

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p!=player && !(p.hasPermission("deltacore.vanish.bypass"))) {
                p.hidePlayer(plugin, player);
            }

        }

    }

    public void hidePlayer(Player player, boolean skipPrefix) {

        if (skipPrefix) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p!=player && !(p.hasPermission("deltacore.vanish.bypass"))) {
                    p.hidePlayer(plugin, player);
                }
            }

        } else {

        }

    }

    public void showPlayer(Player player) {

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p != player) {

                p.showPlayer(plugin, player);
            }

        }

    }

    public void vanishFromPlayer(Player player) {

        if ( !(player.hasPermission("deltacore.vanish.bypass")) ) {

            playerDataManager.getVanishMap().forEach((uuid, status) -> {

                if (status) {
                    Player vanishedPlayer = Bukkit.getPlayer(uuid);

                    if (vanishedPlayer!=null) {
                        player.hidePlayer(plugin, vanishedPlayer);
                    }

                }


            });

        }

    }

    public void fix() {

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p.hasPermission("deltacore.vanish.bypass")) {

                playerDataManager.getVanishMap().forEach((uuid, status) -> {

                    if (status) {
                        Player vanishedPlayer = Bukkit.getPlayer(uuid);
                        if (vanishedPlayer!=null) {
                            p.showPlayer(plugin, vanishedPlayer);
                        }
                    }

                });

            }
        }

    }

}
