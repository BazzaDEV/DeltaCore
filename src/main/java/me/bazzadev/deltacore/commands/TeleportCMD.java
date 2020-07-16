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
                            teleportPlayerToPos(sender, args);
                            break;
                        default:
                            // Too many or too little arguments. Send proper usage.
                            return false;

                    }

                } else {
                    // Player does NOT have permission.
                    // Send no permission message.
                    sender.sendMessage(Vars.NO_PERMISSION);
                }

            } else {
                // Console is executing the command
                switch (args.length) {
                    case 1:
                    case 3:
                        // Console is attempting to teleport themselves to a set of coordinates.
                        // Console is attempting to teleport itself to a player.
                        // Send error message.
                        commandSender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cConsole can't teleport itself, silly!"));
                        break;
                    case 2:
                        // Console is teleporting a player to another player.
                        teleportPlayerToPlayer(commandSender, args);
                        break;
                    case 4:
                        // Console is teleporting a player to a set of coordinates.
                        teleportPlayerToPos(commandSender, args);
                        break;
                    default:
                        // Too many or too little arguments. Send proper usage.
                        return false;
                }


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

    public static void teleportPlayerToPlayer(CommandSender commandSender, String[] args) {

        Player player1 = Bukkit.getPlayerExact(args[0]);
        Player player2 = Bukkit.getPlayerExact(args[1]);

        if (player1!=null && player2!=null) {
            PaperLib.teleportAsync(player1, player2.getLocation()).thenAccept(result -> {
                if (result) {
                    commandSender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have teleported " + ChatUtil.playerNameWithPrefix(player1) + " &7to " + ChatUtil.playerNameWithPrefix(player2) + "&7's location."));
                    player1.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported to " + ChatUtil.playerNameWithPrefix(player2) + "&7's location."));
                } else {
                    commandSender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cSomething wrong - we couldn't teleport " + ChatUtil.playerNameWithPrefix(player1) + "&cto " + ChatUtil.playerNameWithPrefix(player2) + "&c's location."));
                }
            });

        } else {
            commandSender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe specified player(s) do not exist."));
        }


    }

    public static void teleportToPos(Player sender, String[] args) {

        double x, y, z;

        try {
            x = getCoordinate(sender, sender, args[0], 0);
            y = getCoordinate(sender, sender, args[1], 1);
            z = getCoordinate(sender, sender, args[2], 2);
        } catch(NullPointerException e) {
            return;
        }

        Location location = new Location(sender.getWorld(), x, y, z);

        PaperLib.teleportAsync(sender, location).thenAccept(result -> {
            if (result) {
                sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported to &7&nX:&r &b" + Math.round(x) + " &r&7Y: &b" + Math.round(y) + " &7&nZ:&r &b" + Math.round(z)));
            } else {
                sender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cSomething wrong - we couldn't teleport you to those coordinates."));
            }
        });

    }

    public static void teleportPlayerToPos(CommandSender commandSender, String[] args) {

        Player target = Bukkit.getPlayerExact(args[0]);

        if (target!=null) {

            double x, y, z;

            try {
                x = getCoordinate(commandSender, target, args[1], 0);
                y = getCoordinate(commandSender, target, args[2], 1);
                z = getCoordinate(commandSender, target, args[3], 2);
            } catch(NullPointerException e) {
                return;
            }

            Location location = new Location(target.getWorld(), x, y, z);

            PaperLib.teleportAsync(target, location).thenAccept(result -> {
                if (result) {
                    commandSender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have teleported " + ChatUtil.playerNameWithPrefix(target) + " &7to &7&nX:&r &b" + Math.round(x) + " &r&7Y: &b" + Math.round(y) + " &7&nZ:&r &b" + Math.round(z)));
                    target.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported to &7&nX:&r &b" + Math.round(x) + " &r&7Y: &b" + Math.round(y) + " &7&nZ:&r &b" + Math.round(z)));
                } else {
                    commandSender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cSomething wrong - we couldn't teleport " + ChatUtil.playerNameWithPrefix(target) + " &cto those coordinates."));
                }
            });

        } else {
            commandSender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe specified player(s) do not exist."));
        }

    }

    private static Double getCoordinate(CommandSender commandSender, Player target, String s, int i) {

        if (s.equalsIgnoreCase("~")) {
            switch (i) {
                case 0:
                    return target.getLocation().getX();
                case 1:
                    return target.getLocation().getY();
                case 2:
                    return target.getLocation().getZ();
            }

        } else {

            try {
                return Double.parseDouble(s);

            } catch (NumberFormatException e) {
                commandSender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cYou entered an invalid set of coordinates."));
                return null;
            }

        }

        return null;

    }

}
