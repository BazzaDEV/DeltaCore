package dev.bazza.deltacore.system;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.data.config.Config;
import dev.bazza.deltacore.data.config.ConfigPath;
import dev.bazza.deltacore.data.database.DatabaseManager;
import dev.bazza.deltacore.data.database.DatabaseType;
import dev.bazza.deltacore.data.database.local.JsonDBManager;
import dev.bazza.deltacore.data.database.local.LocalDatabaseManager;
import dev.bazza.deltacore.data.database.local.YamlDBManager;
import dev.bazza.deltacore.data.database.remote.RemoteDatabaseManager;
import dev.bazza.deltacore.system.models.User;

import java.util.HashMap;
import java.util.UUID;

public class Server {

    private final DeltaCore plugin;
    private final Config config;

    private final HashMap<UUID, User> onlineUsers;

    public Server(DeltaCore plugin, Config config) {
        this.plugin = plugin;
        this.config = config;

        this.onlineUsers = new HashMap<>();
    }

    private DatabaseManager databaseManager;

    public void setupDB() {
        String dbType = config.getString(ConfigPath.DATABASE_TYPE).toUpperCase();
        DatabaseType databaseType = DatabaseType.valueOf(dbType);

        switch (databaseType) {
            case YAML:
                databaseManager = new YamlDBManager(plugin);
                break;
            case JSON:
                databaseManager = new JsonDBManager(plugin);
                break;
            default:
                break;
        }

        if (databaseManager instanceof LocalDatabaseManager) {
            ((LocalDatabaseManager) databaseManager).initialize();
        } else if (databaseManager instanceof RemoteDatabaseManager) {
            ((RemoteDatabaseManager) databaseManager).connect();
        }

        databaseManager.load();

        if (!onlineUsers.isEmpty()) {
            onlineUsers.forEach(((uuid, user) -> databaseManager.cacheUser(user)));
        }

    }

    public HashMap<UUID, User> getOnlineUsers() {
        return onlineUsers;
    }
    public User getOnlineUser(UUID uuid) {
        return onlineUsers.getOrDefault(uuid, null);
    }

    public DatabaseManager getDB() {
        return databaseManager;
    }
    public void saveToDB(boolean isReloading) {
        onlineUsers.forEach(((uuid, user) -> {
            if (!isReloading)
                user.setAFK(false);
        }));
        databaseManager.sync();
    }

}
