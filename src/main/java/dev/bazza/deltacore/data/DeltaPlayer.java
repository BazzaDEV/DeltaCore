package dev.bazza.deltacore.data;

import dev.bazza.deltacore.utils.ColorUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeltaPlayer {

    private final UUID uuid;
    private final String IGN;

    // Statuses
    private boolean afk;

    public DeltaPlayer(Player player) {
        this.uuid = player.getUniqueId();
        this.IGN = player.getName();
    }

    public DeltaPlayer(UUID uuid) {
        this.uuid = uuid;

        Player player = Bukkit.getPlayer(uuid);
        this.IGN = player.getName();

    }

    public boolean toggleAfk() {
        afk = !afk;
        return afk;
    }

    public void sendMsg(Component msg) {
        getPlayer().sendMessage(msg);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public String getDisplayName() {
        return getPlayer().getName();
    }

    public UUID getUuid() {
        return uuid;
    }
    public String getIGN() {
        return IGN;
    }

    public boolean isAfk() {
        return afk;
    }


}
