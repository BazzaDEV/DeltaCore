package me.bazzadev.deltacore;

import me.bazzadev.deltacore.afk.AFKCommand;
import me.bazzadev.deltacore.afk.AFKManager;
import me.bazzadev.deltacore.config.PlayerDataConfig;
import me.bazzadev.deltacore.config.events.SetupConfigOnJoin;
import me.bazzadev.deltacore.inventory.PlayerInventoryManager;
import me.bazzadev.deltacore.inventory.commands.ClearInventoryCommand;
import me.bazzadev.deltacore.inventory.commands.LoadInventoryCommand;
import me.bazzadev.deltacore.inventory.commands.SaveInventoryCommand;
import me.bazzadev.deltacore.staffmode.StaffModeManager;
import me.bazzadev.deltacore.staffmode.commands.StaffModeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeltaCore extends JavaPlugin {

    private final PlayerDataConfig playerDataConfig = new PlayerDataConfig(this);

    private final PlayerInventoryManager playerInventoryManager = new PlayerInventoryManager(playerDataConfig);
    private final StaffModeManager staffModeManager = new StaffModeManager(playerDataConfig, playerInventoryManager);
    private final AFKManager afkManager = new AFKManager(playerDataConfig);


    @Override
    public void onEnable() {

        createConfigs();
        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        saveConfigs();
    }

    public void registerCommands() {

        this.getCommand("loadinv").setExecutor(new LoadInventoryCommand(playerInventoryManager));
        this.getCommand("saveinv").setExecutor(new SaveInventoryCommand(playerInventoryManager));
        this.getCommand("clearinv").setExecutor(new ClearInventoryCommand());

        this.getCommand("staffmode").setExecutor(new StaffModeCommand(staffModeManager));

        this.getCommand("afk").setExecutor(new AFKCommand(afkManager));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new SetupConfigOnJoin(playerDataConfig), this);
    }

    public void createConfigs() {
        playerDataConfig.create();
    }

    public void saveConfigs() {
        playerDataConfig.save();

    }

}
