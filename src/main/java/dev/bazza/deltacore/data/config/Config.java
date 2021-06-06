package dev.bazza.deltacore.data.config;

import java.util.HashMap;

public class Config {

    private final ConfigManager configManager;
    private final HashMap<ConfigPath, Object> configMap;

    public Config(ConfigManager configManager) {
        this.configManager = configManager;
        this.configMap = new HashMap<>();
    }

    public void initialize() {

        // data:
        //  database-type:
            set(ConfigPath.DATABASE_TYPE);
        //  remote-config:
        //      MongoDB:
        //          username:
                    set(ConfigPath.MONGODB_USERNAME);
        //          password:
                    set(ConfigPath.MONGODB_PASSWORD);
        //          database:
                    set(ConfigPath.MONGODB_DATABASE);
        //          collection:
                    set(ConfigPath.MONGODB_COLLECTION);

        // auto-afk:
        //  enabled:
            set(ConfigPath.AUTO_AFK_ENABLED);
        //  timeout:
            set(ConfigPath.AUTO_AFK_TIMEOUT);
    }

    private void set(ConfigPath path) {
        configMap.put(path, configManager.getValueAt(path));
    }

    public Object get(ConfigPath path) {
        return configMap.get(path);

    }
}
