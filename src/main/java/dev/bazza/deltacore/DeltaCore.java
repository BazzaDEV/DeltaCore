package dev.bazza.deltacore;

import dev.bazza.deltacore.afk.AFKManager;
import dev.bazza.deltacore.afk.AfkCMD;
import dev.bazza.deltacore.commands.Commands;
import dev.bazza.deltacore.data.config.Config;
import dev.bazza.deltacore.data.config.ConfigManager;
import dev.bazza.deltacore.data.Server;
import dev.bazza.deltacore.listeners.PlayerJoinListener;
import dev.bazza.deltacore.listeners.PlayerLeaveListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeltaCore extends JavaPlugin {

    private final ConfigManager configManager = new ConfigManager(this);
    private final Config config = new Config(configManager);
    private final Server server = new Server(this, config);
    private final AFKManager afkManager = new AFKManager(server);

    @Override
    public void onEnable() {
        configManager.initialize();
        config.initialize();

        server.setupDB();

        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        configManager.save();
    }

    private void registerCommands() {
        getCommand(Commands.AFK.getName()).setExecutor(new AfkCMD(afkManager));

    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(server), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(server, afkManager), this);
    }
}
