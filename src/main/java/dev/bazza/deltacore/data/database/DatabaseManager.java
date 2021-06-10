package dev.bazza.deltacore.data.database;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.system.models.User;

import java.util.HashMap;
import java.util.UUID;

public abstract class DatabaseManager {

    protected final HashMap<UUID, User> userCache;

    protected final DeltaCore plugin;

    protected DatabaseManager(DeltaCore plugin) {
        this.plugin = plugin;

        userCache = new HashMap<>();
    }

    /*********************************************************************************************/

    public HashMap<UUID, User> getUserCache() {
        return userCache;
    }

    public void cacheUser(User user) {
        userCache.put(user.getUuid(), user);
    }

    /*********************************************************************************************/

    public abstract void sync();

    public abstract boolean isUser(UUID uuid);
    public abstract User getUser(UUID uuid);

    /*********************************************************************************************/

    public static final ExclusionStrategy EXCLUSION_STRATEGY = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }

        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            return field.getAnnotation(Exclude.class) != null;
        }
    };

}
