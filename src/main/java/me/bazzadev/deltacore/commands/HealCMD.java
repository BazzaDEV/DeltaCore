package me.bazzadev.deltacore.commands;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("heal")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.hasPermission("deltacore.heal")) {

                    player.setHealth(20);
                    player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX +  "&7You have been &ahealed&7."));

                } else {
                    player.sendMessage(Vars.NO_PERMISSION);
                }

                return true;

            } else {
                sender.sendMessage("Only players can use this command. Sorry console. :(");
            }

            return true;

        }

        return false;
    }

}
