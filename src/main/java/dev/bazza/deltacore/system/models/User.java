package dev.bazza.deltacore.system.models;

import dev.bazza.deltacore.data.database.Exclude;
import dev.bazza.deltacore.system.models.roles.OnlineRole;
import dev.bazza.deltacore.system.models.roles.UserRole;
import dev.bazza.deltacore.utils.ColorUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class User {

    @Exclude
    private UserRole role;

    private final UUID uuid;
    private final String IGN;

    // AFK
    private boolean afk;
    private long lastActiveTime;

    // Test
    private String note;

    public static User newUser(UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        return (p != null) ? ( new User(uuid, p.getName(), false, new Date().getTime(), null) ) : (null);

    }

    public User(UUID uuid, String ign, boolean afk, long lastActiveTime, String note) {
        role = new OnlineRole();

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
        getBukkitPlayer().sendMessage(msg);
    }
    public void sendMsg(String msg) {
        getBukkitPlayer().sendMessage(ColorUtil.color(msg));
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public String getDisplayName() {
        return getBukkitPlayer().getName();
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

    public boolean isOnline() {
        return (role instanceof OnlineRole);
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("UUID: ").append(uuid).append("\n")
                .append("Username: ").append(IGN).append("\n")
                .append("Online: ").append(role.toString()).append("\n")
                .append("AFK: ").append(afk).append("\n")
                .append("Last Active Time: ").append(lastActiveTime).append("\n")
                .append("Note: ").append(note)
                .toString().trim();

    }
}
