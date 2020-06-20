package me.bazzadev.deltacore.staffmode.commands;

import me.bazzadev.deltacore.staffmode.StaffModeManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class StaffModeCommand implements CommandExecutor {

    private final StaffModeManager staffModeManager;

    public StaffModeCommand (StaffModeManager staffModeManager) {
        this.staffModeManager = staffModeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] strings) {

        if (command.getName().equalsIgnoreCase("staffmode") && sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("deltacore.staffmode")) {
                staffModeManager.toggle(player);

            } else {
                player.sendMessage(Vars.NO_PERMISSION);
            }

            return true;

        }

        return false;
    }
}
