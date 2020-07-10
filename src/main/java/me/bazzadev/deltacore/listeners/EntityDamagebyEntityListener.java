package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagebyEntityListener implements Listener {

    private final PlayerUtil playerUtil;

    public EntityDamagebyEntityListener(PlayerUtil playerUtil) {
        this.playerUtil = playerUtil;
    }

    @EventHandler
    public void onEntityDamagebyEntityEvent(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();

            if (playerUtil.isVanished(damager)) {
                event.setCancelled(true);
                damager.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Can't deal damage while vanished."));

            }

        }

    }

}
