package me.bazzadev.deltacore.utilities;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerUtil {

    public static int[] getCoords(Player player) {

        Location location = player.getLocation();

        return new int[] { location.getBlockX(), location.getBlockY(), location.getBlockZ() };
    }


}
