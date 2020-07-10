package me.bazzadev.deltacore.tasks;

import me.bazzadev.deltacore.managers.AFKManager;
import me.bazzadev.deltacore.managers.PlayerActivityManager;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;

public class CheckIfAFKTask extends BukkitRunnable {

    private final PlayerActivityManager playerActivityManager;
    private final AFKManager afkManager;
    private final PlayerUtil playerUtil;

    public CheckIfAFKTask(PlayerActivityManager playerActivityManager, AFKManager afkManager, PlayerUtil playerUtil) {
        this.playerActivityManager = playerActivityManager;
        this.afkManager = afkManager;
        this.playerUtil = playerUtil;
    }


    @Override
    public void run() {

        playerActivityManager.getPlayerTimes().forEach((player, time) -> {

            if (!playerUtil.isAFK(player)) {

                long currentTime = new Date().getTime();
                long timeElapsed = currentTime - time;

                if (timeElapsed > Vars.AUTO_AFK_TIMER_MILLISECONDS) {
                    afkManager.toggle(player);
                }

            }

        });

    }


}
