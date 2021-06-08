package dev.bazza.deltacore.commands;

import dev.bazza.deltacore.DeltaCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCMD implements CommandExecutor {

    private final DeltaCore plugin;

    public ReloadCMD(DeltaCore plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase(Commands.RELOAD.getName())) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                plugin.reloadPlugin();
                p.sendMessage("The config has been reloaded.");

            } else {
                commandSender.sendMessage("The config has been reloaded.");
            }

            return true;
        }

        return false;
    }
}
