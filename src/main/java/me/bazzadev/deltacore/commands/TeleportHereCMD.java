package me.bazzadev.deltacore.commands;

import io.papermc.lib.PaperLib;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportHereCMD implements CommandExecutor {

        @Override
        public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

            if (command.getName().equalsIgnoreCase("tphere")) {

                if (commandSender instanceof Player) {
                    // Player is executing the command
                    Player sender = (Player) commandSender;

                    if (sender.hasPermission("deltacore.tphere")) {
                        // Player has permission to run command
                        if (args.length==1) {
                            teleportPlayerHere(sender, args);

                        } else {
                            // Too many arguments. Send proper usage.
                            return false;
                        }

                    } else {
                        // Player does NOT have permission.
                        // Send no permission message.
                        sender.sendMessage(Vars.NO_PERMISSION);
                    }

                } else {
                    // Console is executing the command
                    // Send error message.
                    commandSender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThis command can only be executed by a player."));


                }

                return true;

            }

            return false;

        }

        public static void teleportPlayerHere(Player sender, String[] args) {

            Player target = Bukkit.getPlayerExact(args[0]);

            if (target!=null) {
                PaperLib.teleportAsync(target, sender.getLocation()).thenAccept(result -> {
                    if (result) {
                        sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have teleported " + ChatUtil.playerNameWithPrefix(target) + "&7to your location."));
                        target.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported to " + ChatUtil.playerNameWithPrefix(sender) + "&7's location."));
                    } else {
                        sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cSomething wrong - we couldn't teleport " + ChatUtil.playerNameWithPrefix(target) + "&cto your location."));
                    }
                });

            } else {
                sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe specified player does not exist."));
            }

        }

}
