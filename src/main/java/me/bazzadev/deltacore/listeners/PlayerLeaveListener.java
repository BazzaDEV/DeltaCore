package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.staffmode.StaffGUIManager;
import me.bazzadev.deltacore.utilities.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {

    private final StaffGUIManager staffGUIManager;

    public PlayerLeaveListener(StaffGUIManager staffGUIManager) {
        this.staffGUIManager = staffGUIManager;
    }

    private final String leavePrefix = ChatColor.translateAlternateColorCodes('&', "&8[&c-&8] ");

    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent event) {

        Player player = event.getPlayer();
        String playerName = player.getName();

        event.setQuitMessage(leavePrefix + ChatColor.GOLD + playerName + " has left the server.");

        staffGUIManager.removePlayerFromCache(player, SkullCreator.getHeadWithPlayerData(player));

    }

}
