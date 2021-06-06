package dev.bazza.deltacore.listeners;

import dev.bazza.deltacore.afk.AFKManager;
import dev.bazza.deltacore.data.DeltaPlayer;
import dev.bazza.deltacore.data.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaveListener implements Listener {

    private final Server server;
    private final AFKManager afkManager;

    public PlayerLeaveListener(Server server, AFKManager afkManager) {
        this.server = server;
        this.afkManager = afkManager;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        DeltaPlayer player = server.getPlayers().get(uuid);


        // Set player AFK to false
        if (player.isAfk())
            afkManager.toggleAFK(player, false, false);


        server.getDB().updatePlayer(player);
        server.getPlayers().remove(uuid);


    }
}
