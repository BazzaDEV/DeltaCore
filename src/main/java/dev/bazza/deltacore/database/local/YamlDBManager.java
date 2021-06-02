package dev.bazza.deltacore.database.local;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.data.DeltaPlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class YamlDBManager extends LocalDatabaseManager{

    public YamlDBManager(DeltaCore plugin) {
        super(plugin);
    }

    private static final String FILE_PATH = "db.yml";

    private File file;
    private FileConfiguration config;

    @Override
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

    @Override
    public void load() {

    }

    @Override
    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + file.getName());
        }
    }

    @Override
    public boolean isPlayer(UUID uuid) {
        if (config.getConfigurationSection("players") != null) {
            Set<String> uuids = config.getConfigurationSection("players").getKeys(false);
            return uuids.contains(uuid.toString());
        } else {
            return false;
        }


    }

    @Override
    public DeltaPlayer getPlayerFromDB(UUID uuid) {
        if (isPlayer(uuid)) {
            return new DeltaPlayer(uuid);

        }
        return null;
    }

    @Override
    public void updatePlayer(DeltaPlayer player) {
        String rootPath = "players." + player.getUuid();

        config.set(rootPath + ".IGN", player.getIGN());
        save();
    }


}
