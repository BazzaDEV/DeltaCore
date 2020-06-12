package me.bazzadev.deltacore.inventory.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearInventoryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("clearinv")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (player.hasPermission("staffmode.clearinv.self")) {
                    player.getInventory().clear();

                } else {
                    player.sendMessage(ChatColor.RED + "No permission, bud.");
                }

                return true;

            }


        }

        return false;
    }
}
