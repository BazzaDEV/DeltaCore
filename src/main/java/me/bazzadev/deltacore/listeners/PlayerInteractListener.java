package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.StaffGUIManager;
import me.bazzadev.deltacore.managers.StaffModeManager;
import me.bazzadev.deltacore.managers.VanishManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.bazzadev.deltacore.staffmode.StaffModeItems.viewPlayerList;

public class PlayerInteractListener implements Listener {

    private final StaffGUIManager staffGUIManager;

    public PlayerInteractListener(StaffGUIManager staffGUIManager) {
        this.staffGUIManager = staffGUIManager;
    }

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        // Vanish Mode listener //

        if (VanishManager.isVanished(player)) {

            Block clickedBlock = event.getClickedBlock();

            assert clickedBlock != null;
            Material clickedType = clickedBlock.getType();

            if (clickedType != Material.AIR) {

                if (clickedType==Material.CHEST || clickedType==Material.TRAPPED_CHEST || clickedBlock.getState() instanceof ShulkerBox || clickedType==Material.ENDER_CHEST) {

                    event.setCancelled(true);
                    Inventory inventory = null;

                    if (clickedType==Material.CHEST || clickedType == Material.TRAPPED_CHEST) {

                        Chest chest = (Chest) clickedBlock.getState();

                        inventory = Bukkit.createInventory(player, chest.getInventory().getSize());
                        inventory.setContents(chest.getInventory().getContents());

                        player.openInventory(inventory);
                        player.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Opening chest in &f&lSILENT MODE &7(can't edit contents)."));

                    } else if (clickedBlock.getState() instanceof ShulkerBox) {

                        ShulkerBox shulkerbox = (ShulkerBox) clickedBlock.getState();

                        inventory = Bukkit.createInventory(player, shulkerbox.getInventory().getSize());
                        inventory.setContents(shulkerbox.getInventory().getContents());

                        player.openInventory(inventory);
                        player.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Opening shulker box in &f&lSILENT MODE &7(can't edit contents)."));

                    } else if (clickedType == Material.ENDER_CHEST) {

                        player.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Cannot directly open &dEnder Chests &7in vanish mode."));
                        player.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Use the Staff Mode GUI instead."));

                        player.openInventory(staffGUIManager.getMainGUI());

                    }

                }

            }

        }

        // Staff Mode listener //

        if (StaffModeManager.getStatus(player)) {

            ItemStack itemUsed = event.getItem();

            if (itemUsed==null) return;

            if (event.getAction()==Action.RIGHT_CLICK_AIR || event.getAction()==Action.RIGHT_CLICK_BLOCK ) {

                if (itemUsed.getItemMeta().equals(viewPlayerList.getItemMeta())) {
                    // View Player List
                    event.setCancelled(true);
                    event.getPlayer().openInventory(staffGUIManager.getMainGUI());

                }
            }



        }

        // Check if Player is looking at another player's inventory //

        String invName = player.getOpenInventory().getTitle();

        if (invName.equals("Player")) {
            event.setCancelled(true);

        }

    }
}
