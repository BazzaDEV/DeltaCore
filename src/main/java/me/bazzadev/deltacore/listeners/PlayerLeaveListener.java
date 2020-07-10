package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.PlayerActivityManager;
import me.bazzadev.deltacore.managers.StaffGUIManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private final StaffGUIManager staffGUIManager;
    private final PlayerActivityManager playerActivityManager;

    public PlayerLeaveListener(StaffGUIManager staffGUIManager, PlayerActivityManager playerActivityManager) {
        this.staffGUIManager = staffGUIManager;
        this.playerActivityManager = playerActivityManager;
    }

    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent event) {

        Player player = event.getPlayer();
        String playerName = player.getName();

        event.setQuitMessage(Vars.SERVER_LEAVE_MESSAGE_PREFIX + ChatColor.GOLD + playerName + " has left the server.");

        staffGUIManager.removePlayerFromCache(player);
        playerActivityManager.removePlayer(player);

    }

}
