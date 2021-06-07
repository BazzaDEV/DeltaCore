package dev.bazza.deltacore.system;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.afk.AFKManager;
import dev.bazza.deltacore.afk.CheckAfkTask;
import dev.bazza.deltacore.data.config.Config;
import org.bukkit.scheduler.BukkitTask;

public class TaskManager {

    private final DeltaCore plugin;
    private final Server server;
    private final Config config;
    private final AFKManager afkManager;

    private boolean tasksRunning;

    private BukkitTask checkAfkTask;

    public TaskManager(DeltaCore plugin, Server server, Config config, AFKManager afkManager) {
        this.plugin = plugin;
        this.server = server;
        this.config = config;
        this.afkManager = afkManager;
    }

    public void runTasks() {
        if (tasksRunning)
            cancelTasks();

        checkAfkTask = new CheckAfkTask(server, config, afkManager).runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public void cancelTasks() {
        checkAfkTask.cancel();
        tasksRunning = false;
    }



}
