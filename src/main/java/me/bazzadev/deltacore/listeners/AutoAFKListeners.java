package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.PlayerActivityManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class AutoAFKListeners implements Listener {
    private final PlayerActivityManager playerActivityManager;

    public AutoAFKListeners(PlayerActivityManager playerActivityManager) {
        this.playerActivityManager = playerActivityManager;
    }


    @EventHandler
    public void onPlayerAsyncChat(AsyncPlayerChatEvent event) {
        playerActivityManager.updateTime(event.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if(event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockY() != event.getTo().getBlockY() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            playerActivityManager.updateTime(event.getPlayer());
        }

    }


}
