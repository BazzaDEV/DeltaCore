package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.staffmode.PlayerActionsInventory;
import me.bazzadev.deltacore.managers.StaffGUIManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
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
        Player targetPlayer;

        if (player.getOpenInventory().getTitle().equals("Player")) {

            event.setCancelled(true);
            return;

        }

        if (event.getView().getTitle().startsWith(PlayerActionsInventory.INV_TITLE)) {

            event.setCancelled(true);

            if (itemClicked.equals(Vars.GO_BACK)) {
                player.openInventory(staffGUIManager.getMainGUI());

            } else if (itemClicked.getType().equals(Material.CHEST)) {
                // targetPlayer = PlayerActionsInventory.getPlayerFromGUIHead(player);
                // player.openInventory(targetPlayer.getInventory());

            } else if (itemClicked.getType().equals(Material.ENDER_PEARL)) {
                targetPlayer = PlayerActionsInventory.getPlayerFromGUIHead(player);
                player.teleport(targetPlayer.getLocation());
                player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported to " + ChatUtil.playerNameWithPrefix(targetPlayer) + "&7's location."));

            }

        } else if (event.getView().getTitle().equals(StaffGUIManager.MAINGUI_INV_TITLE)) {

            event.setCancelled(true);

            String targetUUIDString = ChatColor.stripColor(itemClicked.getItemMeta().getLore().get(0)).replace("UUID: ", "").trim();
            UUID targetUUID = UUID.fromString(targetUUIDString);
            staffGUIManager.openPlayerActionsGUI(player, targetUUID);

        }


    }
}
