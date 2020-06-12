package me.bazzadev.deltacore.staffmode;

import me.bazzadev.deltacore.config.PlayerDataConfig;
import me.bazzadev.deltacore.inventory.PlayerInventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class StaffModeManager {

    private final PlayerDataConfig playerDataConfig;
    private final PlayerInventoryManager playerInventoryManager;

    private Player player;
    private String pathToStaffMode;

    public StaffModeManager(PlayerDataConfig playerDataConfig, PlayerInventoryManager playerInventoryManager) {
        this.playerDataConfig = playerDataConfig;
        this.playerInventoryManager = playerInventoryManager;
    }


    public void toggle(Player p) {

        player = p;
        pathToStaffMode = player.getUniqueId().toString() + ".staffmode";

        if ( playerDataConfig.get().getBoolean(pathToStaffMode) ) {
            disable();
        } else {
            enable();
        }

    }

    public void enable() {

        playerDataConfig.get().set(pathToStaffMode, true);
        playerDataConfig.save();

        playerInventoryManager.saveContents(player);
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You have &aentered &7Staff Mode."));

    }

    public void disable() {

        playerDataConfig.get().set(pathToStaffMode, false);
        playerDataConfig.save();

        player.getInventory().clear();
        playerInventoryManager.loadContents(player);
        player.setGameMode(GameMode.SURVIVAL);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You have &cleft &7Staff Mode."));

    }




}
