package me.bazzadev.deltacore.commands;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.managers.ItemSearchManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SearchCMD implements CommandExecutor {

    private final DeltaCore plugin;

    public SearchCMD(DeltaCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("search")) {

            if (sender instanceof Player) {
                // Player is sending this command.
                Player player = (Player) sender;

                if (player.hasPermission("deltacore.search")) {
                    // Player has permission to execute this command.

                    if (args.length==1) {
                        // Player is looking for item and DOES NOT specify metadata
                        Material material = Material.matchMaterial(args[0]);

                        if (material!=null) {
                            // Material is acceptable.
                            new ItemSearchManager(plugin).newSearch(material, player);

                        } else {
                            // Material is null.
                            // Send error message.
                            player.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cThe item specified is not a valid material."));
                        }

                    } else if (args.length > 1) {
                        player.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cMake sure you use &c&lUNDERSCORES &r&c(&7_&c) instead of spaces in the material name."));
                    }

                } else {
                    // Player does not have permission to executing this command.
                    // Send no permission message.
                    player.sendMessage(Vars.NO_PERMISSION);
                }

            } else {
                // Console is executing this command.
                // Send error message.

            }

            return true;

        }

        return false;
    }

}
