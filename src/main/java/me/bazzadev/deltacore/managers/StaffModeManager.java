package me.bazzadev.deltacore.managers;

import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.staffmode.StaffModeItems;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.Vars;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bson.Document;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class StaffModeManager {

    private final PlayerInventoryManager playerInventoryManager;
    private final NamebarManager namebarManager;
    private final PlayerDataManager playerDataManager;
    private final PlayerUtil playerUtil;

    private static final String BASE_PATH = "staffmode-data.survival-inventory";
    private static final String[] BASE_PATH_ARR = { "staffmode-data", "survival-inventory" };

    public StaffModeManager(PlayerInventoryManager playerInventoryManager, NamebarManager namebarManager, PlayerDataManager playerDataManager, PlayerUtil playerUtil) {
        this.playerInventoryManager = playerInventoryManager;
        this.namebarManager = namebarManager;
        this.playerDataManager = playerDataManager;
        this.playerUtil = playerUtil;
    }


    public void toggle(Player player) {

        if ( playerUtil.isStaffMode(player) ) {
            disable(player);
        } else {
            enable(player);
        }

    }

    private void enable(Player player) {

        storeData(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);
        setupStaffInventory(player);

        namebarManager.update(player);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7You have &aentered &7Staff Mode."));


    }

    private void disable(Player player) {

        loadData(player);
        player.setGameMode(GameMode.SURVIVAL);

        namebarManager.update(player);

        sendExitOptions(player);
    }

    private void setupStaffInventory(Player player) {
        player.getInventory().setItem(0, StaffModeItems.viewPlayerList);
    }

    private void sendExitOptions(Player player) {

        player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have &cleft &7Staff Mode."));

        TextComponent yes = new TextComponent(ChatUtil.color("&8[&a&l✔&8] &aYes"));
        TextComponent no = new TextComponent(ChatUtil.color("&8[&c&l✘&8] &cNo"));

        String worldName = getStoredWorld(player);
        String[] coords = getStoredCoords(player);

        yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/staffmode tppos" + " " + player.getName() + " " + worldName + " " + coords[0] + " " + coords[1] + " " + coords[2]));
        TextComponent toSend = new TextComponent();
        toSend.addExtra(yes);
        toSend.addExtra("  ");
        toSend.addExtra(no);

        ChatUtil.sendEmptyLines(100, player);

        player.sendMessage(ChatUtil.color("&dWould you like to be teleported back to your original location?"));
        player.spigot().sendMessage(toSend);

    }

    private void storeData(Player player) {

        Location location = player.getLocation();
        UUID uuid = player.getUniqueId();
        String uuidString = uuid.toString();

        String world = location.getWorld().getName();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        playerDataManager.getStaffmodeMap().put(uuid, true);

        PlayerDataManager.getDatabaseCollection().updateOne(
                Filters.eq("uuid", uuidString),
                combine(set("staffmode-data.originallocation.World", world),
                        set("staffmode-data.originallocation.X", x),
                        set("staffmode-data.originallocation.Y", y),
                        set("staffmode-data.originallocation.Z", z)));

        playerInventoryManager.saveContents(player, BASE_PATH);

    }

    private void loadData(Player player) {

        UUID uuid = player.getUniqueId();
        playerDataManager.getStaffmodeMap().put(uuid, false);

        player.getInventory().clear();
        playerInventoryManager.loadContents(player, BASE_PATH_ARR);

    }

    private Document getStaffModeData(Player player) {

        String playerUUIDString = player.getUniqueId().toString();

        Document filter = new Document("uuid", playerUUIDString);
        Document playerData = PlayerDataManager.getDatabaseCollection().find(filter).first();

        return (Document) playerData.get("staffmode-data");
    }

    private String getStoredWorld(Player player) {

        Document location = (Document) getStaffModeData(player).get("originallocation");

        return location.getString("World");

    }

    private String[] getStoredCoords(Player player) {

        Document location = (Document) getStaffModeData(player).get("originallocation");

        String x = String.valueOf(location.getInteger("X"));
        String y = String.valueOf(location.getInteger("Y"));
        String z = String.valueOf(location.getInteger("Z"));

        return new String[] { x, y, z };

    }


}
