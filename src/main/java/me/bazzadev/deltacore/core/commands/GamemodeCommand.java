package me.bazzadev.deltacore.core.commands;

import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("gms")) {

            if (sender instanceof Player) {
                //sender is a player on the server
                Player player = (Player) sender;

                // check if player is setting own gamemode or other player's gamemode
                if (args.length == 0) {
                    //player is setting own gamemode
                    if (player.hasPermission("deltacore.gms")) {
                        changeGamemode(player, GameMode.SURVIVAL);

                    } else {
                        player.sendMessage(Vars.NO_PERMISSION);
                    }
                } else if (args.length == 1) {
                    //player is setting someone else's gamemode
                    if (player.hasPermission("deltacore.gms.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            changeGamemode(target, GameMode.SURVIVAL);
                        } else {
                            player.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.RED + "The specified player does not exist. Try again.");
                        }
                    } else {
                        player.sendMessage(Vars.NO_PERMISSION);
                    }
                }

            } else {
                // Console is sending the command

                if (args.length > 0) {
                    // Console's trying to change its own gamemode
                    sender.sendMessage(ChatColor.RED + "You can't change the console's gamemode, silly!");

                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        changeGamemode(target, GameMode.SURVIVAL);
                    } else {
                        sender.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.RED + "The specified player does not exist. Try again.");
                    }
                }

            }

            return true;


        } else if (command.getName().equalsIgnoreCase("gmc")) {

            if (sender instanceof Player) {
                //sender is a player on the server
                Player player = (Player) sender;

                // check if player is setting own gamemode or other player's gamemode
                if (args.length == 0) {
                    //player is setting own gamemode
                    if (player.hasPermission("deltacore.gmc")) {
                        changeGamemode(player, GameMode.CREATIVE);

                    } else {
                        player.sendMessage(Vars.NO_PERMISSION);
                    }
                } else if (args.length == 1) {
                    //player is setting someone else's gamemode
                    if (player.hasPermission("deltacore.gmc.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            changeGamemode(target, GameMode.CREATIVE);
                        } else {
                            player.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.RED + "The specified player does not exist. Try again.");
                        }
                    } else {
                        player.sendMessage(Vars.NO_PERMISSION);
                    }
                }

            } else {
                // Console is sending the command

                if (args.length > 0) {
                    // Console's trying to change its own gamemode
                    sender.sendMessage(ChatColor.RED + "You can't change the console's gamemode, silly!");

                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        changeGamemode(target, GameMode.CREATIVE);
                    } else {
                        sender.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.RED + "The specified player does not exist. Try again.");
                    }
                }

            }

            return true;


        } else if (command.getName().equalsIgnoreCase("gma")) {

            if (sender instanceof Player) {
                //sender is a player on the server
                Player player = (Player) sender;

                // check if player is setting own gamemode or other player's gamemode
                if (args.length == 0) {
                    //player is setting own gamemode
                    if (player.hasPermission("deltacore.gma")) {
                        changeGamemode(player, GameMode.ADVENTURE);

                    } else {
                        player.sendMessage(Vars.NO_PERMISSION);
                    }
                } else if (args.length == 1) {
                    //player is setting someone else's gamemode
                    if (player.hasPermission("deltacore.gma.others")) {
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target != null) {
                            changeGamemode(target, GameMode.ADVENTURE);
                        } else {
                            player.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.RED + "The specified player does not exist. Try again.");
                        }
                    } else {
                        player.sendMessage(Vars.NO_PERMISSION);
                    }
                }

            } else {
                // Console is sending the command

                if (args.length > 0) {
                    // Console's trying to change its own gamemode
                    sender.sendMessage(ChatColor.RED + "You can't change the console's gamemode, silly!");

                } else if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        changeGamemode(target, GameMode.ADVENTURE);
                    } else {
                        sender.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.RED + "The specified player does not exist. Try again.");
                    }
                }

            }

            return true;

        }

        return false;
    }

    private void changeGamemode(Player player, GameMode gameMode) {

        if (player.getGameMode() == gameMode) {
            player.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.LIGHT_PURPLE + "Hey, you're already in this gamemode!");

        } else {
            player.setGameMode(gameMode);
            player.sendMessage(Vars.PLUGIN_PREFIX + ChatColor.GRAY + "You're now in " + ChatColor.LIGHT_PURPLE + gameMode.toString().toLowerCase() + ChatColor.GRAY + ".");
        }

    }



}
