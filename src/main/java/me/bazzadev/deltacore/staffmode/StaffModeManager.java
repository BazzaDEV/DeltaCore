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

    private final PlayerInventoryManager playerInventoryManager;

    private Player player;
    private String playerUUIDString;

    public StaffModeManager(PlayerInventoryManager playerInventoryManager) {
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

        PlayerDataManager.getDatabaseCollection().updateOne(
                Filters.eq("uuid", playerUUIDString),
                set("status.staffmode", true));

        playerInventoryManager.saveContents(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);

        setupStaffInventory();

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7You have &aentered &7Staff Mode."));


    }

    public void disable() {

        PlayerDataManager.getDatabaseCollection().updateOne(
                Filters.eq("uuid", playerUUIDString),
                set("status.staffmode", false));

        player.getInventory().clear();
        playerInventoryManager.loadContents(player);
        player.setGameMode(GameMode.SURVIVAL);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7You have &cleft &7Staff Mode."));

    }

    public static boolean getStatus(Player player) {

        String playerUUIDString = player.getUniqueId().toString();

        Document filter = new Document("uuid", playerUUIDString);
        Document playerData = PlayerDataManager.getDatabaseCollection().find(filter).first();
        Document statusData = (Document) playerData.get("status");

        return statusData.getBoolean("staffmode");

    }

    private void setupStaffInventory() {
        player.getInventory().setItem(0, StaffModeItems.viewPlayerList);
    }




}
