package dev.bazza.deltacore.database.local;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.data.DeltaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class YamlDBManager extends LocalDatabaseManager{

    public YamlDBManager(DeltaCore plugin) {
        super(plugin);
    }

    private static final String FILE_PATH = "db.yml";


    /************************************************************************/

    private static final String IGN_PATH = ".IGN";
    private static final String AFK_STATUS_PATH = ".status.afk";

    /************************************************************************/


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
    public DeltaPlayer createPlayerFromDB(UUID uuid) {
        if (isPlayer(uuid)) { // An entry for this UUID exists in the database
            Player p = Bukkit.getPlayer(uuid);
            String rootPath = getRootPathForPlayer(uuid);

            String IGN;
            boolean afk, random;

            /****************************************************
             * Creating the DeltaPlayer Object
             * --------------------------------------------------
             * Each of the above variables is set to the
             * appropriate value from the database, or is assigned
             * the default value if this entry is not available.
             ***************************************************/

            // IGN
            if (config.contains(rootPath + IGN_PATH))
                IGN = config.getString(rootPath + IGN_PATH);
            else
                IGN = p.getName();

            // AFK Status
            if (config.contains(rootPath + AFK_STATUS_PATH))
                afk = config.getBoolean(rootPath + AFK_STATUS_PATH);
            else
                afk = false;

            // All done, return DeltaPlayer object.
            return new DeltaPlayer(uuid, IGN, afk);

        } else { // No entry exists for this UUID
            return null;

        }
    }

    @Override
    public void updatePlayer(DeltaPlayer player) {
        String rootPath = getRootPathForPlayer(player.getUuid());

        // IGN
        config.set(rootPath + IGN_PATH, player.getIGN());
        // AFK Status
        config.set(rootPath + AFK_STATUS_PATH, player.isAfk());


        save();
    }

    private String getRootPathForPlayer(UUID uuid) {
        return "players." + uuid;
    }
}
