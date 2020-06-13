package me.bazzadev.deltacore.config;

import me.bazzadev.deltacore.DeltaCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MongoDBConfig {

    private final DeltaCore plugin;

    public MongoDBConfig(DeltaCore plugin) {
        this.plugin = plugin;
    }

    File mongoDBConfigFile;
    private FileConfiguration mongoDBConfig;

    public void create() {

        mongoDBConfigFile = new File(plugin.getDataFolder(), "mongodb.yml");
        if (!mongoDBConfigFile.exists()) {
            mongoDBConfigFile.getParentFile().mkdirs();
            plugin.saveResource("mongodb.yml", false);
        }

        mongoDBConfig = new YamlConfiguration();
        try {
            mongoDBConfig.load(mongoDBConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public FileConfiguration get() {
        return this.mongoDBConfig;
    }

    public void save() {
        try {
            mongoDBConfig.save(mongoDBConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
            plugin.getLogger().warning("Unable to save " + mongoDBConfigFile.getName());
        }
    }

}
