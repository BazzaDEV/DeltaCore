package dev.bazza.deltacore.listeners;

import dev.bazza.deltacore.data.DeltaPlayer;
import dev.bazza.deltacore.data.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final Server server;

    public PlayerJoinListener(Server server) {
        this.server = server;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        DeltaPlayer player;

        if (server.getDB().isPlayer(uuid)) { // Existing player
            player = server.getDB().getPlayerFromDB(uuid);

        } else { // New player
            player = new DeltaPlayer(uuid);
            server.getDB().updatePlayer(player);

        }

        server.getPlayers().put(uuid, player);

    }
}
