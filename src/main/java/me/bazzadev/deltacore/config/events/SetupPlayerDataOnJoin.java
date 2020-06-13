package me.bazzadev.deltacore.config.events;
import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.utilities.PlayerDataManager;

import org.bson.Document;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.mongodb.client.model.Updates.set;

public class SetupPlayerDataOnJoin implements Listener {

    private final PlayerDataManager playerDataManager;

    public SetupPlayerDataOnJoin(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();
        String playerName = player.getName();
        String playerUUIDString = player.getUniqueId().toString();

        if ( playerDataManager.getDatabaseCollection().countDocuments(new Document("uuid", playerUUIDString)) == 0) {
            playerDataManager.initializePlayer(player);
        } else {
            Document filter = new Document("uuid", playerUUIDString);
            Document playerData = playerDataManager.getDatabaseCollection().find(filter).first();

            if ( !(playerData.getString("IGN").equalsIgnoreCase(playerName)) ) {
                playerDataManager.getDatabaseCollection().updateOne(
                        Filters.eq(filter),
                        set("IGN", playerName));
            }
        }

    }

}
