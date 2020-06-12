package me.bazzadev.deltacore.afk;

import me.bazzadev.deltacore.config.PlayerDataConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AFKManager {

    private final PlayerDataConfig playerDataConfig;
    private String pathToAFKStatus;

    private Player player;
    private String playerName;


    public AFKManager(PlayerDataConfig playerDataConfig) {
        this.playerDataConfig = playerDataConfig;
    }

    public void toggle(Player player) {

        this.player = player;
        pathToAFKStatus = player.getUniqueId().toString() + ".afk";
        playerName = player.getName();

        if ( playerDataConfig.get().getBoolean(pathToAFKStatus) ) {
            disable();
        } else {
            enable();
        }

    }

    public void enable() {

        playerDataConfig.get().set(pathToAFKStatus, true);
        playerDataConfig.save();

        player.setPlayerListName(ChatColor.GRAY + playerName);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&fAFK&8] &7You are now AFK."));

    }

    public void disable() {

        playerDataConfig.get().set(pathToAFKStatus, false);
        playerDataConfig.save();

        player.setPlayerListName(ChatColor.RESET + playerName);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&fAFK&8] &7You are no longer AFK."));

    }

    public boolean getStatus(Player player) {
        return playerDataConfig.get().getBoolean(player.getUniqueId().toString() + ".afk");
    }

}
