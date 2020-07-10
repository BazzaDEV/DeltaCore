package me.bazzadev.deltacore.utilities;

import me.bazzadev.deltacore.managers.PlayerDataManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerUtil {

    private final PlayerDataManager playerDataManager;

    public PlayerUtil(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    public boolean isAFK(Player player) {
        return playerDataManager.getAfkMap().get(player.getUniqueId());
    }

    public boolean isStaffMode(Player player) {
        return playerDataManager.getStaffmodeMap().get(player.getUniqueId());
    }

    public boolean isVanished(Player player) {
        return playerDataManager.getVanishMap().get(player.getUniqueId());
    }

    public static int[] getCoords(Player player) {

        Location location = player.getLocation();

        return new int[] { location.getBlockX(), location.getBlockY(), location.getBlockZ() };
    }


}
