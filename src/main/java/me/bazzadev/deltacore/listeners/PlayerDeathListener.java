package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.PlayerInventoryManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final PlayerInventoryManager playerInventoryManager;

    public PlayerDeathListener(PlayerInventoryManager playerInventoryManager) {
        this.playerInventoryManager = playerInventoryManager;
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();
        playerInventoryManager.saveContents(player, Vars.LAST_DEATH_INVENTORY_PATH);




    }

}
