package dev.bazza.deltacore.system;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.afk.AFKManager;
import dev.bazza.deltacore.afk.CheckAfkTask;
import dev.bazza.deltacore.data.config.Config;
import dev.bazza.deltacore.data.config.ConfigPath;
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

        checkAfkTask = null;
    }

    public void runTasks() {
        if (tasksRunning)
            cancelTasks();

        if (config.getBoolean(ConfigPath.AUTO_AFK_ENABLED))
            checkAfkTask = new CheckAfkTask(server, config, afkManager).runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public void cancelTasks() {
        if (checkAfkTask != null)
            checkAfkTask.cancel();

        tasksRunning = false;
    }



}
