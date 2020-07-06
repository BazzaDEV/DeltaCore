package me.bazzadev.deltacore.listeners;

import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.managers.NamebarManager;
import me.bazzadev.deltacore.managers.VanishManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerDataManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.mongodb.client.model.Updates.set;

public class PlayerJoinListener implements Listener {

    private final PlayerDataManager playerDataManager;
    private final VanishManager vanishManager;
    private final NamebarManager namebarManager;

    private final String joinPrefix = ChatColor.translateAlternateColorCodes('&', "&8[&a+&8] ");

    public PlayerJoinListener(PlayerDataManager playerDataManager, VanishManager vanishManager, NamebarManager namebarManager) {
        this.playerDataManager = playerDataManager;
        this.vanishManager = vanishManager;
        this.namebarManager = namebarManager;
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();
        String playerName = player.getName();
        String playerUUIDString = player.getUniqueId().toString();

        // Custom join message
        event.setJoinMessage(joinPrefix + ChatColor.GOLD + playerName + " has joined the server.");

        // Getting Database stuff setup
        if ( PlayerDataManager.getDatabaseCollection().countDocuments(new Document("uuid", playerUUIDString)) == 0) {
            // Player has no database entry; go ahead and create for them.
            playerDataManager.initializePlayer(player);

        } else {
            // Player has database info.

            // Check if player's IGN has changed since last login.
            // If true, update database IGN with new IGN.
            Document filter = new Document("uuid", playerUUIDString);
            Document playerData = PlayerDataManager.getDatabaseCollection().find(filter).first();

            if ( !(playerData.getString("IGN").equalsIgnoreCase(playerName)) ) {
                PlayerDataManager.getDatabaseCollection().updateOne(
                        Filters.eq(filter),
                        set("IGN", playerName));
            }

        }

        // Setup vanish statuses //

        // Hide vanished players from joined player
        vanishManager.vanishFromPlayer(player);

        if (VanishManager.isVanished(player)) {
            // Player was vanished before they last disconnected.
            // Re-hide the player from other players.
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

        DeltaCore.newChain()
                .delay(10)
                .sync(() -> namebarManager.update(player))
                .execute();

    }

}
