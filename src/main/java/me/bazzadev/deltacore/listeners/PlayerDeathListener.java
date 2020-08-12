package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.PlayerDataManager;
import me.bazzadev.deltacore.managers.PlayerInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    private final PlayerInventoryManager playerInventoryManager;
    private final PlayerDataManager playerDataManager;

    public PlayerDeathListener(PlayerInventoryManager playerInventoryManager, PlayerDataManager playerDataManager) {
        this.playerInventoryManager = playerInventoryManager;
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {

        Player player = event.getEntity();
        playerInventoryManager.saveContents(player, playerDataManager.getLastDeathInvMap());




    }

}
