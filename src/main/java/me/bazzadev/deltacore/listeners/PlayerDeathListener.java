package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.PlayerInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private static final String BASE_PATH = "last-death.inventory";

    private final PlayerInventoryManager playerInventoryManager;

    public PlayerDeathListener(PlayerInventoryManager playerInventoryManager) {
        this.playerInventoryManager = playerInventoryManager;
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();
        playerInventoryManager.saveContents(player, BASE_PATH);




    }

}
