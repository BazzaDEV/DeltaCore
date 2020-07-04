package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.VanishManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (VanishManager.isVanished(player)) {

            event.setCancelled(true);
            player.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Can't break blocks while vanished."));

        }

    }

}
