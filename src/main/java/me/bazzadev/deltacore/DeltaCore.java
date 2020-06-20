package me.bazzadev.deltacore;

import me.bazzadev.deltacore.afk.AFKCommand;
import me.bazzadev.deltacore.afk.AFKManager;
import me.bazzadev.deltacore.config.MongoDBConfig;
import me.bazzadev.deltacore.core.commands.CoordsCommand;
import me.bazzadev.deltacore.core.commands.GamemodeCommand;
import me.bazzadev.deltacore.core.commands.HealCommand;
import me.bazzadev.deltacore.listeners.PlayerCommandPreProcessListener;
import me.bazzadev.deltacore.listeners.PlayerJoinListener;
import me.bazzadev.deltacore.inventory.PlayerInventoryManager;
import me.bazzadev.deltacore.inventory.commands.ClearInventoryCommand;
import me.bazzadev.deltacore.inventory.commands.LoadInventoryCommand;
import me.bazzadev.deltacore.inventory.commands.SaveInventoryCommand;
import me.bazzadev.deltacore.listeners.PlayerLeaveListener;
import me.bazzadev.deltacore.staffmode.StaffModeManager;
import me.bazzadev.deltacore.staffmode.commands.StaffModeCommand;
import me.bazzadev.deltacore.utilities.PlayerDataManager;

import org.bukkit.plugin.java.JavaPlugin;

public final class DeltaCore extends JavaPlugin {

    private final MongoDBConfig mongoDBConfig = new MongoDBConfig(this);
    private final PlayerDataManager playerDataManager = new PlayerDataManager(mongoDBConfig);

    private final PlayerInventoryManager playerInventoryManager = new PlayerInventoryManager(playerDataManager);
    private final StaffModeManager staffModeManager = new StaffModeManager(playerDataManager, playerInventoryManager);
    private final AFKManager afkManager = new AFKManager(playerDataManager);




    @Override
    public void onEnable() {

        createConfigs();

        registerCommands();
        registerEvents();

        playerDataManager.initialize();

    }

    @Override
    public void onDisable() {
        saveConfigs();
    }



    public void createConfigs() {
        mongoDBConfig.create();
    }

    public void saveConfigs() {
        mongoDBConfig.save();

    }

    public void registerCommands() {

        this.getCommand("coords").setExecutor(new CoordsCommand());
        this.getCommand("heal").setExecutor(new HealCommand());

        this.getCommand("gms").setExecutor(new GamemodeCommand());
        this.getCommand("gmc").setExecutor(new GamemodeCommand());
        this.getCommand("gma").setExecutor(new GamemodeCommand());

        this.getCommand("loadinv").setExecutor(new LoadInventoryCommand(playerInventoryManager));
        this.getCommand("saveinv").setExecutor(new SaveInventoryCommand(playerInventoryManager));
        this.getCommand("clearinv").setExecutor(new ClearInventoryCommand());

        this.getCommand("staffmode").setExecutor(new StaffModeCommand(staffModeManager));

        this.getCommand("afk").setExecutor(new AFKCommand(afkManager));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandPreProcessListener(), this);
    }

}
