package dev.bazza.deltacore.data.database;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.system.DeltaPlayer;

import java.util.UUID;

public abstract class DatabaseManager {

    protected final DeltaCore plugin;

    protected DatabaseManager(DeltaCore plugin) {
        this.plugin = plugin;
    }

    public abstract void load();
    public abstract void save();

    public abstract boolean isPlayer(UUID uuid);
    public abstract DeltaPlayer createPlayerFromDB(UUID uuid);
    public abstract void updatePlayer(DeltaPlayer player);

}
