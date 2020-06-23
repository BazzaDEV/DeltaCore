package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.staffmode.PlayerActionsInventory;
import me.bazzadev.deltacore.staffmode.StaffGUIManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InventoryClickListener implements Listener {

    private final StaffGUIManager staffGUIManager;

    public InventoryClickListener(StaffGUIManager staffGUIManager) {
        this.staffGUIManager = staffGUIManager;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        ItemStack itemClicked = event.getCurrentItem();
        if (itemClicked == null || itemClicked.getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().startsWith(PlayerActionsInventory.INV_TITLE)) {
            event.setCancelled(true);
            if (itemClicked.equals(Vars.GO_BACK)) {
                player.openInventory(staffGUIManager.getMainGUI());
            } else if (itemClicked.getType().equals(Material.CHEST)) {
                ItemStack targetHead = player.getOpenInventory().getTopInventory().getItem(PlayerActionsInventory.SLOT_PLAYER_HEAD);
                UUID targetUUID = UUID.fromString(ChatColor.stripColor(targetHead.getLore().get(0)).replace("UUID: ", "").trim());
                Player targetPlayer = Bukkit.getPlayer(targetUUID);
                player.openInventory(targetPlayer.getInventory());
            }

        } else if (event.getView().getTitle().equals(StaffGUIManager.INV_TITLE)) {
            event.setCancelled(true);
            String targetUUIDString = ChatColor.stripColor(itemClicked.getLore().get(0)).replace("UUID: ", "").trim();
            UUID targetUUID = UUID.fromString(targetUUIDString);
            staffGUIManager.openPlayerActionsGUI(player, targetUUID);

        }


    }
}
