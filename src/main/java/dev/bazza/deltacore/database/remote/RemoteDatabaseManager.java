package dev.bazza.deltacore.database.remote;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.database.DatabaseManager;

public abstract class RemoteDatabaseManager extends DatabaseManager {

    protected RemoteDatabaseManager(DeltaCore plugin) {
        super(plugin);
    }

    public abstract void connect();
}
