package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {

        if ( event.getMessage().toLowerCase().startsWith("/gamemode") ) {

            event.setCancelled(true);

            Player sender = event.getPlayer();
            sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&cPlease use the DeltaCore gamemode commands. For more info, type &f/deltacore help"));
        }

    }

}
