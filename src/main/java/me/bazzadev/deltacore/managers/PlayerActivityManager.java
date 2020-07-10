package me.bazzadev.deltacore.managers;

import me.bazzadev.deltacore.utilities.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;

public class PlayerActivityManager {

    private final AFKManager afkManager;
    private final PlayerUtil playerUtil;

    private final HashMap<Player, Long> playerTimes = new HashMap<>();

    public PlayerActivityManager(AFKManager afkManager, PlayerUtil playerUtil) {
        this.afkManager = afkManager;
        this.playerUtil = playerUtil;
    }

    public void updateTime(Player player) {

        playerTimes.put(player, new Date().getTime());

        if (playerUtil.isAFK(player)) {
            afkManager.toggle(player);
        }

    }

    public void removePlayer(Player player) {
        playerTimes.remove(player);
    }

    public HashMap<Player, Long> getPlayerTimes() {
        return playerTimes;
    }


}
