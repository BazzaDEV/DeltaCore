package me.bazzadev.deltacore.managers;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.config.PlayerDataConfig;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class VanishManager {

    private final DeltaCore plugin;
    private final PlayerDataConfig config;

    public VanishManager(DeltaCore plugin, PlayerDataConfig config) {
        this.plugin = plugin;
        this.config = config;
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

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p!=player) {
                p.hidePlayer(plugin, player);
            }

        }


    }

    private void unvanish(Player player) {

        UUID uuid = player.getUniqueId();
        vanishedPlayers.remove(uuid);

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p != player) {
                p.showPlayer(plugin, player);
            }
        }

    }

    public static boolean isVanished(Player player) {

        return vanishedPlayers.contains(player.getUniqueId());

    }




//    public void saveToFile() {
//
//        List<String> uuidList = new ArrayList<>();
//
//        vanishedPlayers.forEach(uuid -> {
//            uuidList.add(uuid.toString());
//        });
//
//        config.get().set("vanished-players", uuidList);
//
//    }
//
//    public void loadFromFile() {
//
//        List<String> uuidList = config.get().getStringList("vanished-players");
//
//        uuidList.forEach(uuidString -> {
//
//            Player player = Bukkit.getPlayer(UUID.fromString(uuidString));
//
//        });
//
//    }

}
