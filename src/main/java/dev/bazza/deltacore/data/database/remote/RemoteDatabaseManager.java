package dev.bazza.deltacore.data.database.remote;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.data.database.DatabaseManager;

public abstract class RemoteDatabaseManager extends DatabaseManager {

    protected RemoteDatabaseManager(DeltaCore plugin) {
        super(plugin);
    }

    public abstract void connect();
}
