package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.staffmode.StaffModeInventory;
import me.bazzadev.deltacore.staffmode.StaffModeManager;
import me.bazzadev.deltacore.utilities.ColorUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static me.bazzadev.deltacore.staffmode.StaffModeItems.viewPlayerList;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onItemClickEvent(PlayerInteractEvent event) {

        ItemStack itemUsed = event.getItem();
        Player player = event.getPlayer();

        if (!StaffModeManager.getStatus(player)) return;

        if (itemUsed == null || itemUsed.getType() == Material.AIR) return;

        if (!itemUsed.getItemMeta().equals(viewPlayerList.getItemMeta())) return;

        if (event.getAction()== Action.LEFT_CLICK_AIR || event.getAction()==Action.LEFT_CLICK_BLOCK) {
            player.sendMessage(ColorUtil.translate(Vars.PLUGIN_PREFIX + "&cTry right-clicking to access the menu."));
            return;
        }

        event.setCancelled(true);
        event.getPlayer().openInventory(new StaffModeInventory().createGUI());
    }
}
