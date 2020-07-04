package me.bazzadev.deltacore.commands;

import me.bazzadev.deltacore.managers.VanishManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VanishCMD implements CommandExecutor {

    private final VanishManager vanishManager;

    public VanishCMD(VanishManager vanishManager) {
        this.vanishManager = vanishManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("vanish")) {

            if (sender instanceof Player) {
                // A player is executing the command
                Player player = (Player) sender;

                if (args.length==0) {
                    // Player is vanishing themselves

                    if (player.hasPermission("deltacore.vanish")) {
                        // Player has permission to vanish themselves

                        // Toggle player's own vanish status
                        vanishManager.toggle(player);

                    } else {
                        // Player has no permission. Send no permission message.
                        player.sendMessage(Vars.NO_PERMISSION);

                    }

                    return true;

                }

            }

        }

        return false;

    }
}
