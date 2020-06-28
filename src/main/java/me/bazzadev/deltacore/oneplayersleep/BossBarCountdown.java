package me.bazzadev.deltacore.oneplayersleep;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BossBarCountdown {

    private final DeltaCore plugin;

    public BossBarCountdown(DeltaCore plugin) {
        this.plugin = plugin;
    }

    private BossBar bar;
    private int taskID = -1;

    public void addPlayer(Player player) {
        bar.addPlayer(player);
    }

    public void addAllPlayers() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(p);
        }
    }

    public BossBar getBar() {
        return bar;
    }

    public void createBar() {

        bar = Bukkit.createBossBar(ChatUtil.color("&c&lPlayer has slept"), BarColor.RED, BarStyle.SOLID);

        addAllPlayers();
        bar.setVisible(true);

        startCountdown();
    }

    public void startCountdown() {

        new BukkitRunnable() {

            double timeLeft = 10;
            double progress = 1.0;
            final double time = 1.0/(10);

            @Override
            public void run() {

                bar.setProgress(progress);

                if (timeLeft > 0) {
                    bar.setTitle(ChatUtil.color("&bSleeping through the night in &f" + timeLeft + " &fseconds&b!"));

                    timeLeft = timeLeft - 1;
                    progress = progress - time;

                } else {
                    bar.setVisible(false);
                    this.cancel();
                }


            }
        }.runTaskTimer(plugin, 0, 20);



//        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
//
//            double timeLeft = 7;
//            double progress = 1.0;
//            final double time = 1.0/(7 * 20);
//
//            @Override
//            public void run() {
//                bar.setProgress(progress);
//
//                if ( timeLeft > 0 ) {
//
//                    bar.setTitle(ChatUtil.color("&bSleeping through the night in &f" + timeLeft + " &fseconds&b!"));
//
//                    timeLeft = timeLeft - time;
//                    progress = progress - time;
//
//
//                } else {
//                    Bukkit.getScheduler().cancelTask(taskID);
//                    Bukkit.broadcastMessage("else");
//                    Bukkit.getServer().removeBossBar(namespacedKey);
//
//                }
//
//            }
//        }, 0, 0);
    }

}
