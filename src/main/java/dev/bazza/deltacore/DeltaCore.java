package dev.bazza.deltacore;

import co.aikar.commands.InvalidCommandArgument;
import co.aikar.commands.PaperCommandManager;
import dev.bazza.deltacore.afk.AFKManager;
import dev.bazza.deltacore.afk.AfkCMD;
import dev.bazza.deltacore.commands.Commands;
import dev.bazza.deltacore.commands.DeltaCoreCMD;
import dev.bazza.deltacore.commands.NoteCMD;
import dev.bazza.deltacore.commands.ReloadCMD;
import dev.bazza.deltacore.system.DeltaPlayer;
import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.data.config.Config;
import dev.bazza.deltacore.data.config.ConfigManager;
import dev.bazza.deltacore.listeners.AFKListeners;
import dev.bazza.deltacore.listeners.PlayerJoinListener;
import dev.bazza.deltacore.listeners.PlayerLeaveListener;
import dev.bazza.deltacore.system.TaskManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeltaCore extends JavaPlugin {

    private final ConfigManager configManager = new ConfigManager(this);
    private final Config config = new Config(configManager);
    private final Server server = new Server(this, config);
    private final AFKManager afkManager = new AFKManager(server);

    private final TaskManager taskManager = new TaskManager(this, server, config, afkManager);
    private PaperCommandManager commandManager;


    @Override
    public void onEnable() {
        configManager.initialize();
        config.initialize();

        server.setupDB();

        taskManager.runTasks();

        registerEvents();
        registerCommands();

    }

    @Override
    public void onDisable() {
        server.saveToDB(false);

    }

    public void reloadPlugin() {
        taskManager.cancelTasks();
        server.saveToDB(true);

        onEnable();

    }

    private void registerCommands() {
        commandManager = new PaperCommandManager(this);

        commandManager.getCommandReplacements()
                .addReplacements(
                    "deltacore", "deltacore|dc"
                );

        commandManager.getCommandContexts().registerContext(DeltaPlayer.class, c -> {
            DeltaPlayer player = null;
            CommandSender sender = c.getSender();
            if (sender instanceof Player) {
                Player p = (Player) sender;
                player = server.getPlayer(p.getUniqueId());
            }

            if (player == null) {
                throw new InvalidCommandArgument("Could not find that DeltaPlayer");
            }

            return player;

        });

        commandManager.registerCommand(new DeltaCoreCMD());
        commandManager.registerCommand(new ReloadCMD(this));

        commandManager.registerCommand(new AfkCMD(afkManager));
        commandManager.registerCommand(new NoteCMD(server));


    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(server), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(server, afkManager), this);
        getServer().getPluginManager().registerEvents(new AFKListeners(server, afkManager), this);
    }

}
