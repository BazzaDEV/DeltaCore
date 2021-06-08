package dev.bazza.deltacore.data.database.local;

import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.system.DeltaPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class YamlDBManager extends LocalDatabaseManager{

    public YamlDBManager(DeltaCore plugin) {
        super(plugin);
    }

    private static final String FILENAME = "db.yml";


    /************************************************************************/

    private static final String IGN_PATH = ".IGN";
    private static final String AFK_STATUS_PATH = ".status.afk";
    private static final String NOTE_PATH = ".note";

    /************************************************************************/


    private File file;
    private FileConfiguration fileConfiguration;

    @Override
    public void initialize() {
        file = new File(plugin.getDataFolder(), FILENAME);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(FILENAME, false);
        }

        fileConfiguration = new YamlConfiguration();

        try {
            fileConfiguration.load(file);
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
            fileConfiguration.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Unable to save " + file.getName());
        }
    }

    @Override
    public boolean isPlayer(UUID uuid) {
        if (fileConfiguration.getConfigurationSection("players") != null) {
            Set<String> uuids = fileConfiguration.getConfigurationSection("players").getKeys(false);
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

            String IGN, note;
            boolean afk;

            /****************************************************
             * Creating the DeltaPlayer Object
             * --------------------------------------------------
             * Each of the above variables is set to the
             * appropriate value from the database, or is assigned
             * the default value if this entry is not available.
             ***************************************************/

            // IGN
            if (fileConfiguration.contains(rootPath + IGN_PATH))
                IGN = fileConfiguration.getString(rootPath + IGN_PATH);
            else
                IGN = p.getName();

            // AFK Status
            if (fileConfiguration.contains(rootPath + AFK_STATUS_PATH))
                afk = fileConfiguration.getBoolean(rootPath + AFK_STATUS_PATH);
            else
                afk = false;

            // Note
            if (fileConfiguration.contains(rootPath + NOTE_PATH))
                note = fileConfiguration.getString(rootPath + NOTE_PATH);
            else
                note = null;

            // All done, return DeltaPlayer object.
            return new DeltaPlayer(uuid, IGN, afk, new Date().getTime(), note);

        } else { // No entry exists for this UUID
            return null;

        }
    }

    @Override
    public void updatePlayer(DeltaPlayer player) {
        String rootPath = getRootPathForPlayer(player.getUuid());

        // IGN
        fileConfiguration.set(rootPath + IGN_PATH, player.getIGN());
        // AFK Status
        fileConfiguration.set(rootPath + AFK_STATUS_PATH, player.isAfk());
        // Note
        fileConfiguration.set(rootPath + NOTE_PATH, player.getNote());

        save();
    }

    private String getRootPathForPlayer(UUID uuid) {
        return "players." + uuid;
    }
}
