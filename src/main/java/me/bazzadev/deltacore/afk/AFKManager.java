package me.bazzadev.deltacore.afk;

import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.utilities.PlayerDataManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.mongodb.client.model.Updates.set;

public class AFKManager {

    private final PlayerDataManager playerDataManager;

    private Player player;
    private String playerName;
    private String playerUUIDString;


    public AFKManager(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    public void toggle(Player player) {

        this.player = player;
        playerName = player.getName();
        playerUUIDString = player.getUniqueId().toString();


        if ( getStatus(player) ) {
            disable();
        } else {
            enable();
        }

    }

    public void enable() {

        playerDataManager.getDatabaseCollection().updateOne(
                Filters.eq("uuid", playerUUIDString),
                set("status.afk", true));

        player.setPlayerListName(ChatColor.GRAY + playerName);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7You are now AFK."));

    }

    public void disable() {

        playerDataManager.getDatabaseCollection().updateOne(
                Filters.eq("uuid", playerUUIDString),
                set("status.afk", false));

        player.setPlayerListName(ChatColor.RESET + playerName);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',Vars.PLUGIN_PREFIX + "&7You are no longer AFK."));

    }

    public boolean getStatus(Player player) {

        Document filter = new Document("uuid", playerUUIDString);
        Document playerData = playerDataManager.getDatabaseCollection().find(filter).first();
        Document statusData = (Document) playerData.get("status");

        return statusData.getBoolean("afk");
    }

}
