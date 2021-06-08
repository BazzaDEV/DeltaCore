package dev.bazza.deltacore.data.config;

import dev.bazza.deltacore.DeltaCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final DeltaCore plugin;

    public ConfigManager(DeltaCore plugin) {
        this.plugin = plugin;
    }

    private static final String FILE_PATH = "config.yml";

    private File file;
    private FileConfiguration fileConfiguration;

    public void initialize() {
        file = new File(plugin.getDataFolder(), FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveDefaultConfig();
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);

        } catch (InvalidConfigurationException | IOException e) {
            System.out.println("Could not load configuration from file " + FILE_PATH);
            e.printStackTrace();
        }


    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + file.getName());
        }
    }

    public Object getValueAt(ConfigPath path) {
        return getValueAt(path.getPath());
    }

    public Object getValueAt(String path) {
        return fileConfiguration.get(path);
    }

}
