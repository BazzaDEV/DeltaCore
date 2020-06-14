package me.bazzadev.deltacore.core.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoordsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {

        if (command.getName().equalsIgnoreCase("coords")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                String playerName = player.getName();
                int x = player.getLocation().getBlockX();
                int y = player.getLocation().getBlockY();
                int z = player.getLocation().getBlockZ();

                Bukkit.broadcastMessage(ChatColor.GREEN + playerName + ChatColor.DARK_GRAY + ChatColor.BOLD + " >> " + ChatColor.GRAY + ChatColor.UNDERLINE + "X:" + ChatColor.WHITE + " " + x + " " + ChatColor.GRAY + ChatColor.UNDERLINE + "Z:" + ChatColor.WHITE + " " + z + " " + ChatColor.DARK_GRAY + "(" + ChatColor.GRAY + "Y:" + ChatColor.WHITE + " " + y + ChatColor.DARK_GRAY + ")");

            } else {
                sender.sendMessage("You can only execute this command in-game.");
            }

            return true;

        }



        return false;
    }
}
