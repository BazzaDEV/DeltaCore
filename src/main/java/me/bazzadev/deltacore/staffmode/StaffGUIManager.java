package me.bazzadev.deltacore.staffmode;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;


public class StaffGUIManager {

    private final HashMap<UUID, PlayerActionsInventory> playerActionsMap = new HashMap<>();

    private final Inventory mainGUI;
    private boolean isBeingViewed;
    public static final String MAINGUI_INV_TITLE = ChatUtil.color("&dSM &7&l>> &fOnline Players");

    public StaffGUIManager() {
        mainGUI = Bukkit.createInventory(null, 27, MAINGUI_INV_TITLE);
        initializePlayerHeads();
        isBeingViewed = false;

    }

    private void initializePlayerHeads() {
        int index = 0;
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            mainGUI.setItem(index, SkullCreator.getHeadWithPlayerData(p));
            index += 1;
        }
    }

    public void refresh() {
        initializePlayerHeads();
    }


    public Inventory getMainGUI() {
        return mainGUI;
    }

    public boolean getUpdate() {
        return isBeingViewed;
    }

    public void setUpdate(boolean value) {
        isBeingViewed = value;
    }



    public void openPlayerActionsGUI(Player player, Player forPlayer) {

        UUID uuid = forPlayer.getUniqueId();

        if (!playerActionsMap.containsKey(uuid)) {
            // player.sendMessage("inv not in hashmap, creating now");
            playerActionsMap.put(uuid, new PlayerActionsInventory(forPlayer));
        }

        // player.sendMessage("got inv from hashmap");
        player.openInventory(playerActionsMap.get(uuid).getBukkitInventory());

    }

    public void openPlayerActionsGUI(Player player, UUID forUUID) {

        Player forPlayer = Bukkit.getPlayer(forUUID);

        if (!playerActionsMap.containsKey(forUUID)) {
            // player.sendMessage("inv not in hashmap, creating now");
            playerActionsMap.put(forUUID, new PlayerActionsInventory(forPlayer));
        }

        // player.sendMessage("got inv from hashmap");
        player.openInventory(playerActionsMap.get(forUUID).getBukkitInventory());

    }

    public void removePlayerFromCache(Player player, ItemStack head) {

        UUID uuid = player.getUniqueId();
        playerActionsMap.remove(uuid);

        mainGUI.remove(head);
    }






}
