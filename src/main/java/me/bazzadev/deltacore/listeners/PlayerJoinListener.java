package me.bazzadev.deltacore.listeners;

import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.managers.NamebarManager;
import me.bazzadev.deltacore.managers.PlayerDataManager;
import me.bazzadev.deltacore.managers.VanishManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static com.mongodb.client.model.Updates.set;

public class PlayerJoinListener implements Listener {

    private final PlayerDataManager playerDataManager;
    private final VanishManager vanishManager;
    private final NamebarManager namebarManager;
    private final PlayerUtil playerUtil;

    public PlayerJoinListener(PlayerDataManager playerDataManager, VanishManager vanishManager, NamebarManager namebarManager, PlayerUtil playerUtil) {
        this.playerDataManager = playerDataManager;
        this.vanishManager = vanishManager;
        this.namebarManager = namebarManager;
        this.playerUtil = playerUtil;
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();
        String playerName = player.getName();
        UUID uuid = player.getUniqueId();
        String playerUUIDString = uuid.toString();

        // Custom join message
        event.setJoinMessage(Vars.SERVER_JOIN_MESSAGE_PREFIX + ChatColor.GOLD + playerName + " has joined the server.");

        // Getting Database stuff setup
        if ( PlayerDataManager.getDatabaseCollection().countDocuments(new Document("uuid", playerUUIDString)) == 0) {
            // Player has no database entry; go ahead and create for them.
            playerDataManager.initializePlayer(player);

        } else {
            // Player has database info.

            // Check if player's IGN has changed since last login.
            // If true, update database IGN with new IGN.
            Document filter = playerDataManager.getPlayerDocumentAndFilter(uuid)[0];
            Document playerData = playerDataManager.getPlayerDocumentAndFilter(uuid)[1];

            if ( !(playerData.getString("IGN").equalsIgnoreCase(playerName)) ) {
                PlayerDataManager.getDatabaseCollection().updateOne(
                        Filters.eq(filter),
                        set("IGN", playerName));
            }

        }

        if (!playerDataManager.getAfkMap().containsKey(uuid)) {
            playerDataManager.loadPlayerData(playerDataManager.getPlayerDocument(uuid), uuid);
        }

        // Setup vanish statuses //

        // Hide vanished players from joined player
        vanishManager.vanishFromPlayer(player);

        if (playerUtil.isVanished(player)) {
            // Player was vanished before they last disconnected.
            // Re-hide the player from other players.
            // Skip namebar update to prevent Bungeecord crashes. Will be set on next TaskChain.
            vanishManager.hidePlayer(player, true);

            // Wait 20 ticks, then send player message regarding vanished status.
            DeltaCore.newChain()
                    .delay(20)
                    .sync(() -> {
                        player.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Looks like you were vanished before you disconnected last time."));
                        player.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7We went ahead and &d&lVANISHED &7you again."));
                    })
                    .execute();

        }

        // Updating player's namebar.
        // Runs on 10 tick delay to prevent issues with scoreboard and Bungeecord.
        DeltaCore.newChain()
                .delay(5)
                .sync(() -> namebarManager.update(player))
                .execute();

    }

}
