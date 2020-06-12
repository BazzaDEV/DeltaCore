package me.bazzadev.deltacore.config.events;

import me.bazzadev.deltacore.config.PlayerDataConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SetupConfigOnJoin implements Listener {

    private final PlayerDataConfig playerDataConfig;

    public SetupConfigOnJoin(PlayerDataConfig playerDataConfig) {
        this.playerDataConfig = playerDataConfig;
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {

        Player player = event.getPlayer();
        String playerUUIDString = player.getUniqueId().toString();

        if ( !(playerDataConfig.get().isConfigurationSection(playerUUIDString)) ) {

            playerDataConfig.get().createSection(playerUUIDString + ".staffmode");
            playerDataConfig.get().set(playerUUIDString + ".staffmode", false);

            playerDataConfig.get().createSection(playerUUIDString + ".inventory.inv");
            playerDataConfig.get().createSection(playerUUIDString + ".inventory.armor");

            playerDataConfig.get().createSection(playerUUIDString + ".afk");
            playerDataConfig.get().set(playerUUIDString + ".afk", false);

            playerDataConfig.save();

        }


    }

}
