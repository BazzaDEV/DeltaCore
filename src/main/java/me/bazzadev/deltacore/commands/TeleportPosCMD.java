package me.bazzadev.deltacore.commands;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportPosCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("tppos")) {

            if (commandSender instanceof Player) {
                // Player is executing the command
                Player sender = (Player) commandSender;

                if (sender.hasPermission("deltacore.tppos")) {
                    // Player has permission to execute the command
                    switch (args.length) {
                        case 3:
                            // Player is teleporting themselves to a set of coordinates
                            TeleportCMD.teleportToPos(sender, args);
                            break;
                        case 4:
                            // Player is teleporting another player to a set of coordinates
                            TeleportCMD.teleportPlayerToPos(commandSender, args);
                            break;
                        default:
                            // Too little or too many arguments. Send proper usage.
                            return false;
                    }
                } else {
                    // Player does NOT have permission
                    // Send no permission message.
                    sender.sendMessage(Vars.NO_PERMISSION);
                }

            } else {
                // Console is executing the command
                switch (args.length) {
                    case 3:
                        // Console is trying to teleport itself to a set of coordinates.
                        // Send error message.
                        commandSender.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe console cannot teleport itself. Please specify a player, then a set of coordinates."));
                        break;
                    case 4:
                        // Console is teleporting another player to a set of coordinates.
                        TeleportCMD.teleportPlayerToPos(commandSender, args);
                        break;
                    default:
                        // Too many or too little arguments. Send proper usage.
                        return false;
                }


            }

            return true;

        }


        return false;
    }


}
