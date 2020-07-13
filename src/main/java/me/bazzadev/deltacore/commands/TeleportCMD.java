package me.bazzadev.deltacore.commands;

import io.papermc.lib.PaperLib;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("tp")) {

            if (commandSender instanceof Player) {
                // Player is executing the command
                Player sender = (Player) commandSender;

                if (sender.hasPermission("deltacore.tp")) {
                    // Player has permission to run command
                    switch (args.length) {
                        case 1:
                            // Player sender is teleporting themselves to another player.
                            teleportToPlayer(sender, args);
                            break;
                        case 2:
                            // Command sender is teleporting a player to another player.
                            teleportPlayerToPlayer(sender, args);
                            break;
                        case 3:
                            // Player sender is teleporting themselves to a set of coordinates.
                            teleportToPos(sender, args);
                            break;
                        case 4:
                            // Command sender is teleporting a player to a set of coordinates.
                            break;
                        default:
                            return false;

                    }

                } else {
                    // Player does NOT have permission.
                    // Send no permission message.
                    sender.sendMessage(Vars.NO_PERMISSION);
                }

            } else {
                // Console is executing the command



            }

            return true;

        }

       return false;

    }

    public static void teleportToPlayer(Player sender, String[] args) {

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target!=null) {
            PaperLib.teleportAsync(sender, target.getLocation()).thenAccept(result -> {
                if (result) {
                    sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported to " + ChatUtil.playerNameWithPrefix(target) + "&7's location."));
                } else {
                    sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cSomething wrong - we couldn't teleport you to " + ChatUtil.playerNameWithPrefix(target) + "&c's location."));
                }
            });

        } else {
            sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe specified player does not exist."));
        }


    }

    public static void teleportPlayerToPlayer(Player sender, String[] args) {

        Player player1 = Bukkit.getPlayerExact(args[0]);
        Player player2 = Bukkit.getPlayerExact(args[1]);

        if (player1!=null && player2!=null) {
            PaperLib.teleportAsync(player1, player2.getLocation()).thenAccept(result -> {
                if (result) {
                    sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have teleported " + ChatUtil.playerNameWithPrefix(player1) + "&7to " + ChatUtil.playerNameWithPrefix(player2) + "&7's location."));
                    player1.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported to " + ChatUtil.playerNameWithPrefix(player2) + "&7's location."));
                } else {
                    sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cSomething wrong - we couldn't teleport " + ChatUtil.playerNameWithPrefix(player1) + "&cto " + ChatUtil.playerNameWithPrefix(player2) + "&c's location."));
                }
            });

        } else {
            sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe specified player(s) do not exist."));
        }


    }

    public static void teleportToPos(Player sender, String[] args) {

        double x, y, z;

        try {
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            z = Double.parseDouble(args[2]);

        } catch (NumberFormatException e) {
            sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cYou entered an invalid set of coordinates."));
            return;
        }

        Location location = new Location(sender.getWorld(), x, y, z);

        PaperLib.teleportAsync(sender, location).thenAccept(result -> {
            if (result) {
                sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported to &7&nX:&r &a" + x + " &r&7Y: &a" + y + " &7&nZ:&r &a" + z));
            } else {
                sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cSomething wrong - we couldn't teleport you to those coordinates."));
            }
        });

    }

    public static void teleportPlayerToPos(Player sender, String[] args) {


    }

}
