package dev.bazza.deltacore.data.config;

public enum ConfigPath {

    // data:
    //  database-type:
        DATABASE_TYPE("data.database-type"),
    //  remote-config:
    //      MongoDB:
    //          username:
                MONGODB_USERNAME("data.remote-config.MongoDB.username"),
    //          password:
                MONGODB_PASSWORD("data.remote-config.MongoDB.password"),
    //          database:
                MONGODB_DATABASE("data.remote-config.MongoDB.database"),
    //          collection:
                MONGODB_COLLECTION("data.remote-config.MongoDB.collection"),

    // auto-afk:
    //  enabled:
        AUTO_AFK_ENABLED("auto-afk.enabled"),
    //  timeout:
        AUTO_AFK_TIMEOUT("auto-afk.timeout"),

    ;

    private final String path;

    ConfigPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
