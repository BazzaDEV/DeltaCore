package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.VanishManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final PlayerUtil playerUtil;

    public BlockPlaceListener(PlayerUtil playerUtil) {
        this.playerUtil = playerUtil;
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        if (playerUtil.isVanished(player)) {

            event.setCancelled(true);
            player.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Can't place blocks while vanished."));

        }

    }
}
