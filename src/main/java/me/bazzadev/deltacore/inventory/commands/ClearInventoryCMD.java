package me.bazzadev.deltacore.inventory.commands;

import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearInventoryCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("clearinv")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (player.hasPermission("deltacore.clearinv.self")) {
                    player.getInventory().clear();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7Your inventory has been &fcleared&7."));

                } else {
                    player.sendMessage(Vars.NO_PERMISSION);
                }

                return true;

            }


        }

        return false;
    }
}
