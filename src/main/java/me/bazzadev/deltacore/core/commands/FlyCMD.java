package me.bazzadev.deltacore.core.commands;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("fly")) {

            if (sender instanceof Player) {
                // Player is sending command
                Player player = (Player) sender;

                if (args.length==0) {
                    // Player is setting their own flight status
                    toggleFly(player);

                } else if (args.length==1) {
                    // Player is setting another player's flight status
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target==null) {
                        // Player is not online. Send error message
                        player.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe specified player is not online."));

                    } else {
                        //Player is online. Set their flight status
                        toggleFly(target, player);
                    }

                }

            } else {
                // Console is sending command
                if (args.length==0) {
                    // Console can't change their own flight status. Send error message with proper usage.
                    sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cConsole can't change their own flight status."));
                    sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&7Proper usage: &f/fly &2<player>"));

                } else if (args.length==1) {
                    // Console is setting a player's flight status
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target==null) {
                        // Player is not online. Send error message
                        sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe specified player is not online."));

                    } else {
                        //Player is online. Set their flight status
                        toggleFly(target, sender);
                    }

                }

            }

            return true;

        }

        return false;

    }

    private void toggleFly(Player target) {

        if (target.getAllowFlight()) {

            target.setAllowFlight(false);
            target.setFlying(false);

            target.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7Your flight status has been set to &cfalse&7."));

        } else {

            target.setAllowFlight(true);
            target.setFlying(true);

            target.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7Your flight status has been set to &atrue&7."));

        }

    }

    private void toggleFly(Player target, CommandSender sender) {

        if (target.getAllowFlight()) {

            target.setAllowFlight(false);
            target.setFlying(false);

            target.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7Your flight status has been set to &cfalse&7."));

            sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have set " + ChatUtil.playerNameWithPrefix(target) + "&7's flight status to &cfalse&7."));

        } else {

            target.setAllowFlight(true);
            target.setFlying(true);

            target.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7Your flight status has been set to &atrue&7."));

            sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have set " + ChatUtil.playerNameWithPrefix(target) + "&7's flight status to &atrue&7."));

        }

    }

}
