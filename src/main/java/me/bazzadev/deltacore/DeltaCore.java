package me.bazzadev.deltacore;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainFactory;
import me.bazzadev.deltacore.commands.*;
import me.bazzadev.deltacore.config.MongoDBConfig;
import me.bazzadev.deltacore.config.PlayerDataConfig;
import me.bazzadev.deltacore.listeners.*;
import me.bazzadev.deltacore.managers.*;
import me.bazzadev.deltacore.tasks.CheckIfAFKTask;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.managers.PlayerDataManager;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.SkullCreator;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class DeltaCore extends JavaPlugin {

    private final MongoDBConfig mongoDBConfig = new MongoDBConfig(this);
    private final PlayerDataManager playerDataManager = new PlayerDataManager(mongoDBConfig);
    private final PlayerDataConfig playerDataConfig = new PlayerDataConfig(this);

    private final PlayerUtil playerUtil = new PlayerUtil(playerDataManager);
    private final NamebarManager namebarManager = new NamebarManager(playerUtil);

    private final PlayerInventoryManager playerInventoryManager = new PlayerInventoryManager(playerDataManager);
    private final StaffModeManager staffModeManager = new StaffModeManager(playerInventoryManager, namebarManager, playerDataManager, playerUtil);
    private final AFKManager afkManager = new AFKManager(namebarManager, playerDataManager, playerUtil);
    private final VanishManager vanishManager = new VanishManager(this, namebarManager, playerDataManager, playerUtil);

    private final PlayerActivityManager playerActivityManager = new PlayerActivityManager(afkManager, playerUtil);

    private static Chat chat = null;
    private static Permission perms = null;

    private final ChatUtil chatUtil = new ChatUtil(playerUtil);
    private final SkullCreator skullCreator = new SkullCreator(chatUtil);
    private final StaffGUIManager staffGUIManager = new StaffGUIManager(skullCreator);

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
        playerDataManager.loadData();

        BukkitTask checkIfAfkTask = new CheckIfAFKTask(playerActivityManager, afkManager, playerUtil).runTaskTimer(this, 0, 20);

        registerCommands();
        registerEvents();

        staffGUIManager.createGUI();

        vanishManager.fix();

        namebarManager.updateAll();

    }

    @Override
    public void onDisable() {
        playerDataManager.saveData();
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

        this.getCommand("loadinv").setExecutor(new InventoryCMD(playerInventoryManager));
        this.getCommand("saveinv").setExecutor(new InventoryCMD(playerInventoryManager));
        this.getCommand("clearinv").setExecutor(new InventoryCMD(playerInventoryManager));
        this.getCommand("restoreinv").setExecutor(new InventoryCMD(playerInventoryManager));

        this.getCommand("staffmode").setExecutor(new StaffModeCMD(staffModeManager));

        this.getCommand("afk").setExecutor(new AfkCMD(afkManager));
        this.getCommand("fly").setExecutor(new FlyCMD());
        this.getCommand("vanish").setExecutor(new VanishCMD(vanishManager));

        this.getCommand("portalhelper").setExecutor(new PortalHelperCMD());
    }

    private void registerEvents() {

        getServer().getPluginManager().registerEvents(new AutoAFKListeners(playerActivityManager), this);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerDataManager, vanishManager, namebarManager, playerUtil), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveListener(staffGUIManager, playerActivityManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(playerInventoryManager), this);

        getServer().getPluginManager().registerEvents(new EntityDamagebyEntityListener(playerUtil), this);

        getServer().getPluginManager().registerEvents(new PlayerCommandPreProcessListener(), this);

        getServer().getPluginManager().registerEvents(new PlayerInteractListener(staffGUIManager, playerUtil), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(playerUtil), this);

        getServer().getPluginManager().registerEvents(new BlockBreakListener(playerUtil), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(playerUtil), this);

        getServer().getPluginManager().registerEvents(new InventoryClickListener(staffGUIManager, vanishManager, playerUtil), this);
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
