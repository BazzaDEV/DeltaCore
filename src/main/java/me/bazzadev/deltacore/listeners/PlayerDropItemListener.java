package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.utilities.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import static me.bazzadev.deltacore.staffmode.StaffModeItems.viewPlayerList;

public class PlayerDropItemListener implements Listener {

    private final PlayerUtil playerUtil;

    public PlayerDropItemListener(PlayerUtil playerUtil) {
        this.playerUtil = playerUtil;
    }

    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (playerUtil.isStaffMode(player)) {
            if (event.getItemDrop().getItemStack().equals(viewPlayerList)) {
                event.setCancelled(true);
            }
        }

    }

}
