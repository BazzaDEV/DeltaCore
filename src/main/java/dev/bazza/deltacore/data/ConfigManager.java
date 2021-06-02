package dev.bazza.deltacore.data;

import dev.bazza.deltacore.DeltaCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {

    private final DeltaCore plugin;

    public ConfigManager(DeltaCore plugin) {
        this.plugin = plugin;
    }

    private static final String FILE_PATH = "config.yml";

    private File file;
    private FileConfiguration config;

    public void initialize() {
        file = new File(plugin.getDataFolder(), FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(FILE_PATH, false);
        }

        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public Object get(String path) {
        return config.get(path);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + file.getName());
        }
    }

    public Object read(String path) {
        return config.get(path);
    }


}
