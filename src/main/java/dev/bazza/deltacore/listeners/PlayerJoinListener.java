package dev.bazza.deltacore.listeners;

import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.system.models.User;
import dev.bazza.deltacore.system.models.roles.OnlineRole;
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
        User user;

        if (server.getDB().isUser(uuid)) { // Existing user
            user = server.getDB().getUser(uuid);
            user.setRole(new OnlineRole());

        } else { // New user
            user = User.newUser(uuid);

        }

        server.getOnlineUsers().put(uuid, user);
        server.getDB().cacheUser(user);

    }
}
