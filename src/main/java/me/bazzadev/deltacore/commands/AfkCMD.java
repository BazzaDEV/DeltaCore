package me.bazzadev.deltacore.commands;

import me.bazzadev.deltacore.managers.AFKManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AfkCMD implements CommandExecutor {

    private final AFKManager afkManager;

    public AfkCMD(AFKManager afkManager) {
        this.afkManager = afkManager;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {

        if(command.getName().equalsIgnoreCase("afk")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                afkManager.toggle(player);

            } else {
                sender.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "Only players can go AFK."));
                return true;
            }
        }

        return true;
    }


}