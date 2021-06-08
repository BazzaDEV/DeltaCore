package dev.bazza.deltacore.system;

import dev.bazza.deltacore.utils.ColorUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class DeltaPlayer {

    private final UUID uuid;

    private final String IGN;

    // AFK
    private boolean afk;
    private long lastActiveTime;

    // Test
    private String note;

    public static DeltaPlayer newPlayer(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        return (p != null) ? ( new DeltaPlayer(uuid, p.getName(), false, new Date().getTime(), null) ) : (null);

    }

    public DeltaPlayer(UUID uuid, String ign, boolean afk, long lastActiveTime, String note) {
        this.uuid = uuid;
        this.IGN = ign;
        this.afk = afk;
        this.lastActiveTime = lastActiveTime;
        this.note = note;
    }


    public boolean toggleAfk() {
        afk = !afk;
        return afk;
    }

    public void setAFK(boolean afk) {
        this.afk = afk;
    }

    public void updateLastMovedTime(long lastMovedTime) {
        this.lastActiveTime = lastMovedTime;
    }

    public void sendMsg(Component msg) {
        getPlayer().sendMessage(msg);
    }
    public void sendMsg(String msg) {
        getPlayer().sendMessage(ColorUtil.color(msg));
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
    public long getLastActiveTime() {
        return lastActiveTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
