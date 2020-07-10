package me.bazzadev.deltacore.tasks;

import me.bazzadev.deltacore.managers.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AsyncDatabaseTask extends BukkitRunnable {

    private final PlayerDataManager playerDataManager;

    public AsyncDatabaseTask(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @Override
    public void run() {

        Bukkit.getLogger().info("Starting routine async database task. Backing up all data now...");
        playerDataManager.saveData();
        Bukkit.getLogger().info("[SUCCESS] Async database task complete!");

    }
}
