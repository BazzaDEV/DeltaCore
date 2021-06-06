package dev.bazza.deltacore.afk;

import dev.bazza.deltacore.data.DeltaPlayer;
import dev.bazza.deltacore.data.Server;
import dev.bazza.deltacore.utils.ChatUtil;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AFKManager {

    private final Server server;

    public AFKManager(Server server) {
        this.server = server;
    }

    public void toggleAFK(Player p, boolean notify, boolean notifyAll) {
        toggleAFK(p.getUniqueId(), notify, notifyAll);
    }

    public void toggleAFK(DeltaPlayer player, boolean notify, boolean notifyAll) {
        toggleAFK(player.getUuid(), notify, notifyAll);
    }

    public void toggleAFK(UUID uuid, boolean notify, boolean notifyAll) {
        DeltaPlayer player = server.getPlayer(uuid);
        boolean isAfk = player.toggleAfk();

        if (notify)
            player.sendMsg(ChatUtil.AFK_NOTIFY(isAfk));

        if (notifyAll) {
            server.getPlayers().forEach((uuid1, player1) -> {
                if (uuid != uuid1)
                    player1.sendMsg(ChatUtil.AFK_NOTIFY_ALL(isAfk, player));
            });
        }

    }
}
