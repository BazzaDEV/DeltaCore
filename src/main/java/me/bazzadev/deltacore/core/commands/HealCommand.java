package me.bazzadev.deltacore.core.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {

        if (command.getName().equalsIgnoreCase("heal")) {
            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;

                if (player.hasPermission("deltacore.heal")) {

                    player.setHealth(20);
                    player.setFoodLevel(20);

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You have been &ahealed &7and &efed&7."));

                } else {
                    player.sendMessage(ChatColor.RED + "No permission, bud.");
                }

                return true;

            } else {
                commandSender.sendMessage("Only players can use this command. Sorry console. :(");
            }

            return true;

        }

        return false;
    }

}
