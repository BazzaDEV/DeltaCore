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
        Block clickedBlock = event.getClickedBlock();
        Material clickedType = clickedBlock.getType();

        if (VanishManager.isVanished(player)) {

            if (clickedType != Material.AIR) {

                if (clickedType==Material.CHEST || clickedType==Material.TRAPPED_CHEST || clickedType==Material.SHULKER_BOX || clickedType==Material.ENDER_CHEST) {

                    event.setCancelled(true);
                    Inventory inventory = null;

                    if (clickedType==Material.CHEST || clickedType == Material.TRAPPED_CHEST) {

                        Chest chest = (Chest) clickedBlock.getState();

                        inventory = Bukkit.createInventory(player, chest.getInventory().getSize());
                        inventory.setContents(chest.getInventory().getContents());

                        player.openInventory(inventory);
                        player.sendMessage(ChatUtil.color("&7Opening chest in &f&lSILENT MODE &7(can't edit contents)."));

                    } else if (clickedType == Material.SHULKER_BOX) {

                        ShulkerBox shulkerbox = (ShulkerBox) clickedBlock.getState();

                        inventory = Bukkit.createInventory(player, shulkerbox.getInventory().getSize());
                        inventory.setContents(shulkerbox.getInventory().getContents());

                        player.openInventory(inventory);
                        player.sendMessage(ChatUtil.color("&7Opening shulker box in &f&lSILENT MODE &7(can't edit contents)."));

                    } else if (clickedType == Material.ENDER_CHEST) {

                        player.sendMessage(ChatUtil.color("&7Can't open &dEnder Chests &7in vanish mode."));


                    }

                    return;

                }

            }

        }

        ItemStack itemUsed = event.getItem();
        String invName = player.getOpenInventory().getTopInventory().toString();

        if (!StaffModeManager.getStatus(player)) return;

        if (!invName.contains("CraftInventoryCrafting")) return;

        if (itemUsed == null || itemUsed.getType() == Material.AIR) return;

        if (!itemUsed.getItemMeta().equals(viewPlayerList.getItemMeta())) return;

        if (event.getAction()== Action.LEFT_CLICK_AIR || event.getAction()==Action.LEFT_CLICK_BLOCK) {
            player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&cTry right-clicking to access the menu."));
            return;
        }

        event.setCancelled(true);
        event.getPlayer().openInventory(staffGUIManager.getMainGUI());
    }
}
