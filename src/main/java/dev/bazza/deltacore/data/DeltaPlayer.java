package dev.bazza.deltacore.data;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DeltaPlayer {

    private final UUID uuid;

    private final String IGN;

    // Statuses
    private boolean afk;

    public static DeltaPlayer newPlayer(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        return (p != null) ? ( new DeltaPlayer(uuid, p.getName(), false) ) : (null);

    }

    public DeltaPlayer(UUID uuid, String ign, boolean afk) {
        this.uuid = uuid;
        this.IGN = ign;
        this.afk = afk;
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
