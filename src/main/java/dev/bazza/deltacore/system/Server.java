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

import java.util.HashMap;
import java.util.UUID;

public class Server {

    private final DeltaCore plugin;
    private final Config config;

    private final HashMap<UUID, DeltaPlayer> playerMap;

    public Server(DeltaCore plugin, Config config) {
        this.plugin = plugin;
        this.config = config;

        this.playerMap = new HashMap<>();
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

    }

    public HashMap<UUID, DeltaPlayer> getPlayers() {
        return playerMap;
    }
    public DeltaPlayer getPlayer(UUID uuid) {
        return playerMap.getOrDefault(uuid, null);
    }

    public DatabaseManager getDB() {
        return databaseManager;
    }
    public void saveToDB(boolean isReloading) {
        playerMap.forEach(((uuid, player) -> {
            if (!isReloading)
                player.setAFK(false);
            databaseManager.updatePlayer(player);
        }));
    }

}
