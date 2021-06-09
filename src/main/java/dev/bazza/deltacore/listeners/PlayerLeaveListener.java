package dev.bazza.deltacore.listeners;

import dev.bazza.deltacore.afk.AFKManager;
import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.system.models.User;
import dev.bazza.deltacore.system.models.roles.OfflineRole;
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
        User user = server.getOnlineUsers().get(uuid);

        // Set user AFK to false
        if (user.isAfk())
            afkManager.toggleAFK(user, false, false);

        user.setRole(new OfflineRole());
        server.getOnlineUsers().remove(uuid);

    }
}
