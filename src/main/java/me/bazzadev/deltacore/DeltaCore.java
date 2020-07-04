package me.bazzadev.deltacore;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import me.bazzadev.deltacore.commands.*;
import me.bazzadev.deltacore.config.MongoDBConfig;
import me.bazzadev.deltacore.config.PlayerDataConfig;
import me.bazzadev.deltacore.listeners.*;
import me.bazzadev.deltacore.managers.*;
import me.bazzadev.deltacore.utilities.PlayerDataManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class DeltaCore extends JavaPlugin {

    private final MongoDBConfig mongoDBConfig = new MongoDBConfig(this);
    private final PlayerDataManager playerDataManager = new PlayerDataManager(mongoDBConfig);
    private final PlayerDataConfig playerDataConfig = new PlayerDataConfig(this);

    private final PlayerInventoryManager playerInventoryManager = new PlayerInventoryManager(playerDataManager);
    private final StaffModeManager staffModeManager = new StaffModeManager(playerInventoryManager);
    private final AFKManager afkManager = new AFKManager();
    private final VanishManager vanishManager = new VanishManager(this, playerDataConfig);

    private static Chat chat = null;
    private static Permission perms = null;

    private StaffGUIManager staffGUIManager;

    private static TaskChainFactory taskChainFactory;
    public static <T> TaskChain<T> newChain() {
        return taskChainFactory.newChain();
    }
    public static <T> TaskChain<T> newSharedChain(String name) {
        return taskChainFactory.newSharedChain(name);
    }

    @Override
    public void onEnable() {

        taskChainFactory = BukkitTaskChainFactory.create(this);

        createConfigs();

        setupChat();
        setupPermissions();

        playerDataManager.initialize();

        staffGUIManager = new StaffGUIManager();

        registerCommands();
        registerEvents();

    }

    @Override
    public void onDisable() {
        saveConfigs();
    }



    private void createConfigs() {

        playerDataConfig.create();
        mongoDBConfig.create();
    }

    private void saveConfigs() {

        playerDataConfig.save();
        mongoDBConfig.save();
    }

    private void registerCommands() {

        this.getCommand("coords").setExecutor(new CoordsCMD());
        this.getCommand("heal").setExecutor(new HealCMD());
        this.getCommand("feed").setExecutor(new FeedCMD());

        this.getCommand("gms").setExecutor(new GamemodeCMD());
        this.getCommand("gmc").setExecutor(new GamemodeCMD());
        this.getCommand("gma").setExecutor(new GamemodeCMD());
        this.getCommand("gmspec").setExecutor(new GamemodeCMD());

        this.getCommand("loadinv").setExecutor(new LoadInventoryCMD(playerInventoryManager));
        this.getCommand("saveinv").setExecutor(new SaveInventoryCMD(playerInventoryManager));
        this.getCommand("clearinv").setExecutor(new ClearInventoryCMD());
        this.getCommand("restoreinv").setExecutor(new RestoreInventoryCMD(playerInventoryManager));

        this.getCommand("staffmode").setExecutor(new StaffModeCMD(staffModeManager));

        this.getCommand("afk").setExecutor(new AfkCMD(afkManager));
        this.getCommand("fly").setExecutor(new FlyCMD());
        this.getCommand("vanish").setExecutor(new VanishCMD(vanishManager));

        this.getCommand("portalhelper").setExecutor(new PortalHelperCMD());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(staffGUIManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(playerInventoryManager), this);

        getServer().getPluginManager().registerEvents(new PlayerCommandPreProcessListener(), this);

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(staffGUIManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);

        getServer().getPluginManager().registerEvents(new InventoryClickListener(staffGUIManager), this);
        getServer().getPluginManager().registerEvents(new OpenInventoryListener(staffGUIManager, this), this);
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    public static Chat getChat() {
        return chat;
    }

    public static Permission getPerms() {
        return perms;
    }



}
