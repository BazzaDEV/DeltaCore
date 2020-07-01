package me.bazzadev.deltacore.staffmode;

import com.mongodb.client.model.Filters;
import com.nametagedit.plugin.NametagEdit;
import me.bazzadev.deltacore.inventory.PlayerInventoryManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerDataManager;
import me.bazzadev.deltacore.utilities.Vars;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class StaffModeManager {

    private final PlayerInventoryManager playerInventoryManager;

    private static final String BASE_PATH = "staffmode-data.survival-inventory";
    private static final String[] BASE_PATH_ARR = { "staffmode-data", "survival-inventory" };

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

    private void enable() {

        storeData();
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        setupStaffInventory();
        NametagEdit.getApi().setPrefix(player, Vars.PLAYER_STAFFMODE_PREFIX);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7You have &aentered &7Staff Mode."));


    }

    private void disable() {

        loadData();
        player.setGameMode(GameMode.SURVIVAL);
        NametagEdit.getApi().setPrefix(player, "");

        sendExitOptions();
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

    private void sendExitOptions() {

        player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have &cleft &7Staff Mode."));



        TextComponent yes = new TextComponent(ChatUtil.color("&8[&a&l✔&8] &aYes"));
        TextComponent no = new TextComponent(ChatUtil.color("&8[&c&l✘&8] &cNo"));

        String worldName = getStoredWorld();
        String[] coords = getStoredCoords();

        yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/staffmode tppos" + " " + player.getName() + " " + worldName + " " + coords[0] + " " + coords[1] + " " + coords[2]));
        TextComponent toSend = new TextComponent();
        toSend.addExtra(yes);
        toSend.addExtra("  ");
        toSend.addExtra(no);

        ChatUtil.sendEmptyLines(100, player);

        player.sendMessage(ChatUtil.color("&dWould you like to be teleported back to your original location?"));
        player.spigot().sendMessage(toSend);

    }

    private void storeData() {

        Location location = player.getLocation();

        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        PlayerDataManager.getDatabaseCollection().updateOne(
                Filters.eq("uuid", playerUUIDString),
                combine(set("status.staffmode", true),
                        set("staffmode-data.originallocation.World", world),
                        set("staffmode-data.originallocation.X", x),
                        set("staffmode-data.originallocation.Y", y),
                        set("staffmode-data.originallocation.Z", z)));

        playerInventoryManager.saveContents(player, BASE_PATH);

    }

    private void loadData() {

        PlayerDataManager.getDatabaseCollection().updateOne(
                Filters.eq("uuid", playerUUIDString),
                set("status.staffmode", false));

        player.getInventory().clear();
        playerInventoryManager.loadContents(player, BASE_PATH_ARR);

    }

    private Document getStaffModeData() {

        String playerUUIDString = player.getUniqueId().toString();

        Document filter = new Document("uuid", playerUUIDString);
        Document playerData = PlayerDataManager.getDatabaseCollection().find(filter).first();

        return (Document) playerData.get("staffmode-data");
    }

    private String getStoredWorld() {

        Document location = (Document) getStaffModeData().get("originallocation");

        return location.getString("World");

    }

    private String[] getStoredCoords() {

        Document location = (Document) getStaffModeData().get("originallocation");

        String x = String.valueOf(location.getInteger("X"));
        String y = String.valueOf(location.getInteger("Y"));
        String z = String.valueOf(location.getInteger("Z"));

        return new String[] { x, y, z };

    }


}
