package me.bazzadev.deltacore.managers;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.entity.Player;

public class AFKManager {

    private final PlayerDataManager playerDataManager;
    private final PlayerUtil playerUtil;

    public AFKManager(PlayerDataManager playerDataManager, PlayerUtil playerUtil) {
        this.playerDataManager = playerDataManager;
        this.playerUtil = playerUtil;
    }

    public void toggle(Player player) {

        if (playerUtil.isAFK(player)) {
            disable(player);
        } else {
            enable(player);
        }

    }

    private void enable(Player player) {

        playerDataManager.getAfkMap().put(player.getUniqueId(), true);
        player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You are now AFK."));

//        String playerUUIDString = player.getUniqueId().toString();
//
//        DeltaCore.newChain()
//                .asyncFirst(() -> PlayerDataManager.getDatabaseCollection().updateOne(
//                                        Filters.eq("uuid", playerUUIDString),
//                                        set("status.afk", true)))
//                .sync(() -> {
//                    namebarManager.update(player);
//                    player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You are now AFK."));
//                })
//                .execute();

    }

    private void disable(Player player) {

        playerDataManager.getAfkMap().put(player.getUniqueId(), false);
        player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You are no longer AFK."));

//        String playerUUIDString = player.getUniqueId().toString();
//
//        DeltaCore.newChain()
//                .asyncFirst(() -> PlayerDataManager.getDatabaseCollection().updateOne(
//                        Filters.eq("uuid", playerUUIDString),
//                        set("status.afk", false)))
//                .sync(() -> {
//                    namebarManager.update(player);
//                    player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You are no longer AFK."));
//                })
//                .execute();

    }



}
