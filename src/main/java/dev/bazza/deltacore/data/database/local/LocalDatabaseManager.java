package dev.bazza.deltacore.data.database.local;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.data.database.DatabaseManager;

public abstract class LocalDatabaseManager extends DatabaseManager {

    protected LocalDatabaseManager(DeltaCore plugin) {
        super(plugin);
    }

    public abstract void initialize();
}
