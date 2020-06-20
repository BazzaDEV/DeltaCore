package me.bazzadev.deltacore.staffmode;

import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.inventory.PlayerInventoryManager;
import me.bazzadev.deltacore.utilities.PlayerDataManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static com.mongodb.client.model.Updates.set;

public class StaffModeManager {

    private final PlayerDataManager playerDataManager;
    private final PlayerInventoryManager playerInventoryManager;

    private Player player;
    private String playerUUIDString;

    public StaffModeManager(PlayerDataManager playerDataManager, PlayerInventoryManager playerInventoryManager) {
        this.playerDataManager = playerDataManager;
        this.playerInventoryManager = playerInventoryManager;
    }


    public void toggle(Player player) {

        this.player = player;
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
                set("status.staffmode", true));

        playerInventoryManager.saveContents(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7You have &aentered &7Staff Mode."));

    }

    public void disable() {

        playerDataManager.getDatabaseCollection().updateOne(
                Filters.eq("uuid", playerUUIDString),
                set("status.staffmode", false));

        player.getInventory().clear();
        playerInventoryManager.loadContents(player);
        player.setGameMode(GameMode.SURVIVAL);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7You have &cleft &7Staff Mode."));

    }

    public boolean getStatus(Player player) {

        Document filter = new Document("uuid", playerUUIDString);
        Document playerData = playerDataManager.getDatabaseCollection().find(filter).first();
        Document statusData = (Document) playerData.get("status");

        return statusData.getBoolean("staffmode");

    }




}
