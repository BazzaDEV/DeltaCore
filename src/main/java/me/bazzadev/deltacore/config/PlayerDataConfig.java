package me.bazzadev.deltacore.config;

import me.bazzadev.deltacore.DeltaCore;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerDataConfig {

    private final DeltaCore plugin;

    public PlayerDataConfig(DeltaCore plugin) {
        this.plugin = plugin;
    }

    File playerDataConfigFile;
    private FileConfiguration playerDataConfig;

    public void create() {

        playerDataConfigFile = new File(plugin.getDataFolder(), "player-data.yml");
        if (!playerDataConfigFile.exists()) {
            playerDataConfigFile.getParentFile().mkdirs();
            plugin.saveResource("player-data.yml", false);
        }

        playerDataConfig = new YamlConfiguration();
        try {
            playerDataConfig.load(playerDataConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    public FileConfiguration get() {
        return this.playerDataConfig;
    }

    public void save() {
        try {
            playerDataConfig.save(playerDataConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
            plugin.getLogger().warning("Unable to save " + playerDataConfigFile.getName());
        }
    }
}
