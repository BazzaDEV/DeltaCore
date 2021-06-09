package dev.bazza.deltacore.afk;

import dev.bazza.deltacore.system.models.User;
import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.utils.Messages;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AFKManager {

    private final Server server;

    public AFKManager(Server server) {
        this.server = server;
    }

    public void setAllFalse() {
        server.getOnlineUsers().forEach(((uuid, player) -> player.setAFK(false)));
    }

    public void toggleAFK(Player p, boolean notify, boolean notifyAll) {
        toggleAFK(p.getUniqueId(), notify, notifyAll);
    }

    public void toggleAFK(User player, boolean notify, boolean notifyAll) {
        toggleAFK(player.getUuid(), notify, notifyAll);
    }

    public void toggleAFK(UUID uuid, boolean notify, boolean notifyAll) {
        User player = server.getOnlineUser(uuid);
        boolean isAfk = player.toggleAfk();

        if (notify)
            player.sendMsg(Messages.AFK_NOTIFY(isAfk));

        if (notifyAll) {
            server.getOnlineUsers().forEach((uuid1, player1) -> {
                if (!uuid.equals(uuid1))
                    player1.sendMsg(Messages.AFK_NOTIFY_ALL(isAfk, player));
            });
        }

    }
}
