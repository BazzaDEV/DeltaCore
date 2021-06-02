package me.bazzadev.deltacore.managers;

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

public class StaffModeManager {

    private final PlayerInventoryManager playerInventoryManager;
    private final PlayerDataManager playerDataManager;
    private final PlayerUtil playerUtil;

    public static final String STAFFMODE_INV_BASE_PATH = "staffmode-data.survival-inventory";
    public static final String[] STAFFMODE_INV_BASE_PATH_ARR = { "staffmode-data", "survival-inventory" };

    public static final String STAFFMODE_ORIGINAL_LOC_PATH = "staffmode-data.originallocation";
    public static final String[] STAFFMODE_ORIGINAL_LOC_PATH_ARR = { "staffmode-data", "originallocation" };

    public StaffModeManager(PlayerInventoryManager playerInventoryManager, PlayerDataManager playerDataManager, PlayerUtil playerUtil) {
        this.playerInventoryManager = playerInventoryManager;
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

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7You have &aentered &7Staff Mode."));


    }

    private void disable(Player player) {

        loadData(player);
        player.setGameMode(GameMode.SURVIVAL);

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

        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        String[] locationData = new String[] { world, String.valueOf(x), String.valueOf(y), String.valueOf(z)};

        playerDataManager.getStaffmodeMap().put(uuid, true);
        playerDataManager.getStaffmodeLocationMap().put(uuid, locationData);
        playerInventoryManager.saveContents(player, playerDataManager.getStaffmodeInvMap());

    }

    private void loadData(Player player) {

        UUID uuid = player.getUniqueId();
        playerDataManager.getStaffmodeMap().put(uuid, false);

        player.getInventory().clear();
        playerInventoryManager.loadContents(player, playerDataManager.getStaffmodeInvMap());

    }

    private String getStoredWorld(Player player) {

        UUID uuid = player.getUniqueId();

        return playerDataManager.getStaffmodeLocationMap().get(uuid)[0];

    }

    private String[] getStoredCoords(Player player) {

        UUID uuid = player.getUniqueId();
        String[] locationData = playerDataManager.getStaffmodeLocationMap().get(uuid);

        return new String[] { locationData[1], locationData[2], locationData[3] };

    }

    private Document getStaffModeData(Player player) {

        String playerUUIDString = player.getUniqueId().toString();

        Document filter = new Document("uuid", playerUUIDString);
        Document playerData = PlayerDataManager.getDatabaseCollection().find(filter).first();

        return (Document) playerData.get("staffmode-data");
    }


}
