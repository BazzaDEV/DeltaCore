package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.oneplayersleep.SleepManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class PlayerSleepListener implements Listener {

    private final SleepManager sleepManager;

    public PlayerSleepListener(SleepManager sleepManager) {
        this.sleepManager = sleepManager;
    }

    @EventHandler
    public void onPlayerBedEnterEvent(PlayerBedEnterEvent event) {

        Player player = event.getPlayer();

        if (player.getWorld().getTime() < 13000) return;
        if (Bukkit.getOnlinePlayers().size() == 1) return;

        if (!sleepManager.isStarted()) {
            sleepManager.start();
            sleepManager.addPlayer(player);

        } else if (sleepManager.isStarted()) {
            sleepManager.addPlayer(player);
        }

    }

    @EventHandler
    public void onPlayerBedLeaveEvent(PlayerBedLeaveEvent event) {

        Player player = event.getPlayer();

        if (player.getWorld().getTime() < 13000) return;
        if (Bukkit.getOnlinePlayers().size() == 1) return;

        if (sleepManager.isStarted()) {
            sleepManager.removePlayer(player);

            if (sleepManager.getCount()==0) {
                sleepManager.cancel();
            }

        }




    }

}
