package dev.bazza.deltacore.database.local;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.database.DatabaseManager;

public abstract class LocalDatabaseManager extends DatabaseManager {

    protected LocalDatabaseManager(DeltaCore plugin) {
        super(plugin);
    }

    public abstract void initialize();
}
