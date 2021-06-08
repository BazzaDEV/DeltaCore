package dev.bazza.deltacore.listeners;

import dev.bazza.deltacore.afk.AFKManager;
import dev.bazza.deltacore.system.DeltaPlayer;
import dev.bazza.deltacore.system.Server;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Date;
import java.util.UUID;

public class AFKListeners implements Listener {

    private final Server server;
    private final AFKManager afkManager;

    public AFKListeners(Server server, AFKManager afkManager) {
        this.server = server;
        this.afkManager = afkManager;
    }

    private void updateAFKStatus(DeltaPlayer player) {
        player.updateLastMovedTime(new Date().getTime());
        if (player.isAfk())
            afkManager.toggleAFK(player, true, true);

    }

    @EventHandler
    public void onPlayerAsyncChat(AsyncChatEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        updateAFKStatus(server.getPlayer(uuid));

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom(),
                 to = event.getTo();

        if (from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
            UUID uuid = event.getPlayer().getUniqueId();
            updateAFKStatus(server.getPlayer(uuid));
        }

    }

}
