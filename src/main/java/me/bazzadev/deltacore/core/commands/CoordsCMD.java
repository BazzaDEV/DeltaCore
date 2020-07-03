package me.bazzadev.deltacore.core.commands;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CoordsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {

        if (command.getName().equalsIgnoreCase("coords")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                String playerName = player.getName();
                int x = player.getLocation().getBlockX();
                int y = player.getLocation().getBlockY();
                int z = player.getLocation().getBlockZ();

                Bukkit.broadcastMessage(ChatUtil.color(DeltaCore.getChat().getPlayerPrefix(player) + playerName + " &8&l>> &7&nX:&r &f" + x + " &r&7Y: &f" + y + " &7&nZ:&r &f" + z + " &7(" + ChatUtil.coloredWorld(player) + "&7)"));

            } else {
                sender.sendMessage("You can only execute this command in-game.");
            }

            return true;

        }



        return false;
    }
}
