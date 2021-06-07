package dev.bazza.deltacore;

import dev.bazza.deltacore.afk.AFKManager;
import dev.bazza.deltacore.afk.AfkCMD;
import dev.bazza.deltacore.commands.Commands;
import dev.bazza.deltacore.commands.NoteCMD;
import dev.bazza.deltacore.commands.ReloadCMD;
import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.data.config.Config;
import dev.bazza.deltacore.data.config.ConfigManager;
import dev.bazza.deltacore.listeners.AFKListeners;
import dev.bazza.deltacore.listeners.PlayerJoinListener;
import dev.bazza.deltacore.listeners.PlayerLeaveListener;
import dev.bazza.deltacore.system.TaskManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeltaCore extends JavaPlugin {

    private final ConfigManager configManager = new ConfigManager(this);
    private final Config config = new Config(configManager);
    private final Server server = new Server(this, config);
    private final AFKManager afkManager = new AFKManager(server);

    private final TaskManager taskManager = new TaskManager(this, server, config, afkManager);


    @Override
    public void onEnable() {
        configManager.initialize();
        config.initialize();

        server.setupDB();

        taskManager.runTasks();

        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        taskManager.cancelTasks();
        server.saveToDB(false);

    }

    public void reloadPlugin() {
        taskManager.cancelTasks();
        server.saveToDB(true);

        onEnable();

    }

    private void registerCommands() {
        getCommand(Commands.AFK.getName()).setExecutor(new AfkCMD(afkManager));
        getCommand(Commands.RELOAD.getName()).setExecutor(new ReloadCMD(this));

        getCommand(Commands.SET_NOTE.getName()).setExecutor(new NoteCMD(server));
        getCommand(Commands.VIEW_NOTE.getName()).setExecutor(new NoteCMD(server));

    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(server), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(server, afkManager), this);
        getServer().getPluginManager().registerEvents(new AFKListeners(server, afkManager), this);
    }

}
