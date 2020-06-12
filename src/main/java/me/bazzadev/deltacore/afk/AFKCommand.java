package me.bazzadev.deltacore.afk;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AFKCommand implements CommandExecutor {

    private final AFKManager afkManager;

    public AFKCommand(AFKManager afkManager) {
        this.afkManager = afkManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {

        if(command.getName().equalsIgnoreCase("afk")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                afkManager.toggle(player);

            } else {
                sender.sendMessage(ChatColor.RED + "Only players can go AFK.");
                return true;
            }
        }

        return true;
    }


}