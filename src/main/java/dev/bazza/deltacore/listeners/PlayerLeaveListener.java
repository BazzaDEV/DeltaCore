package dev.bazza.deltacore.listeners;

import dev.bazza.deltacore.data.DeltaPlayer;
import dev.bazza.deltacore.data.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaveListener implements Listener {

    private final Server server;

    public PlayerLeaveListener(Server server) {
        this.server = server;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        DeltaPlayer player = server.getPlayers().get(uuid);

        server.getDB().updatePlayer(player);
        server.getPlayers().remove(uuid);


    }
}