package me.bazzadev.deltacore.managers;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.config.PlayerDataConfig;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishManager {

    private final DeltaCore plugin;
    private final PlayerDataConfig config;
    private final NamebarManager namebarManager;

    public VanishManager(DeltaCore plugin, PlayerDataConfig config, NamebarManager namebarManager) {
        this.plugin = plugin;
        this.config = config;
        this.namebarManager = namebarManager;
    }

    private static final ArrayList<UUID> vanishedPlayers = new ArrayList<>();

    public void toggle(Player player) {

        if (vanishedPlayers.contains(player.getUniqueId())) {
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

        vanishedPlayers.add(uuid);
        hidePlayer(player);

    }

    private void unvanish(Player player) {

        UUID uuid = player.getUniqueId();

        vanishedPlayers.remove(uuid);
        showPlayer(player);

    }

    public void hidePlayer(Player player) {

        namebarManager.update(player);

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
            namebarManager.update(player);
        }





    }

    public void showPlayer(Player player) {

        namebarManager.update(player);

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p != player) {

                p.showPlayer(plugin, player);
            }

        }

    }

    public void vanishFromPlayer(Player player) {

        if ( !(player.hasPermission("deltacore.vanish.bypass")) ) {

            for (UUID uuid : vanishedPlayers) {

                Player vanishedPlayer = Bukkit.getPlayer(uuid);
                player.hidePlayer(plugin, vanishedPlayer);

            }

        }

    }

    public void fix() {

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p.hasPermission("deltacore.vanish.bypass")) {

                vanishedPlayers.forEach((uuid -> {
                    Player vanishedPlayer = Bukkit.getPlayer(uuid);
                    p.showPlayer(plugin, vanishedPlayer);
                }));

            }
        }



    }

    public static boolean isVanished(Player player) {

        return vanishedPlayers.contains(player.getUniqueId());

    }

    public void saveToFile() {

        List<String> uuidList = new ArrayList<>();

        vanishedPlayers.forEach(uuid -> {

            uuidList.add(uuid.toString());

        });

        config.get().set("vanished-players", uuidList);
        config.save();

    }

    public void loadFromFile() {

        List<String> uuidList = config.get().getStringList("vanished-players");

        uuidList.forEach(uuidString -> {

            UUID uuid = UUID.fromString(uuidString);
            Player player = Bukkit.getPlayer(uuid);

            if (player!=null) {
                vanish(player);

            } else {
                // This was added so that an offline vanished player gets re-vanished when they login after a reload.
                // This also fixes the issue where NO vanished players get re-vanished after a restart.
                vanishedPlayers.add(uuid);
            }

        });

    }

}
