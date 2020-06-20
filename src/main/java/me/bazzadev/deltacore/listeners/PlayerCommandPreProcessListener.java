package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreProcessListener implements Listener {

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {

        if ( event.getMessage().toLowerCase().startsWith("/gamemode") ) {
            event.setCancelled(true);
            Player sender = event.getPlayer();
            sender.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.RED + "Please use the DeltaCore gamemode commands. For more info, type " + ChatColor.WHITE + "/deltacore help");
        }

    }

}
