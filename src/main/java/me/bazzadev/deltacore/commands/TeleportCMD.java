package me.bazzadev.deltacore.commands;

import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCMD implements CommandExecutor {

    Player sender = null;
    Player receiver = null;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("tp")) {

            if (commandSender instanceof Player) {
                // Player is executing the command
                sender = (Player) commandSender;

                if (sender.hasPermission("deltacore.tp")) {
                    // Player has permission to run command
                    switch (args.length) {
                        case 1:
                            // Player sender is teleporting themselves to another player.

                            break;
                        case 2:
                            // Command sender is teleporting a player to another player.
                        case 4:
                            // Command sender is teleporting themselves to a set of coordinates.
                        default:

                    }

                } else {
                    // Player does NOT have permission.
                    // Send no permission message.
                    sender.sendMessage(Vars.NO_PERMISSION);
                }

            } else {
                // Console is executing the command



            }

            return true;

        }

       return false;
    }

    private void teleport() {

        boolean isPaper = false;
        try {

            isPaper = Class.forName("com.destroystokyo.paper$VersionHistoryManager$VersionData") != null;
        } catch (ClassNotFoundException ignored) {
        }

        if (isPaper) {
            // Server is running Paper, so it's safe to teleport async.

        } else {
            // Server is running Spiogt, teleport sync.

        }



    }

}
