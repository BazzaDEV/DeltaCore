package dev.bazza.deltacore.data;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.data.config.Config;
import dev.bazza.deltacore.data.config.ConfigPath;
import dev.bazza.deltacore.database.DatabaseManager;
import dev.bazza.deltacore.database.DatabaseType;
import dev.bazza.deltacore.database.local.LocalDatabaseManager;
import dev.bazza.deltacore.database.local.YamlDBManager;
import dev.bazza.deltacore.database.remote.RemoteDatabaseManager;

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
        DatabaseType databaseType = DatabaseType.valueOf((String) config.get(ConfigPath.DATABASE_TYPE));

        switch (databaseType) {
            case YAML:
                databaseManager = new YamlDBManager(plugin);
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
}
