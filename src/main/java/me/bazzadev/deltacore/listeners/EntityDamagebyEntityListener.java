package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.managers.VanishManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamagebyEntityListener implements Listener {

    @EventHandler
    public void onEntityDamagebyEntityEvent(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();

            if (VanishManager.isVanished(damager)) {
                event.setCancelled(true);
                damager.sendMessage(ChatUtil.color(Vars.VANISH_PREFIX + "&7Can't deal damage while vanished."));

            }

        }

    }

}
