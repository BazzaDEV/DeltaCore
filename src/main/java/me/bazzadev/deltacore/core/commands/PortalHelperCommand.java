package me.bazzadev.deltacore.core.commands;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.PlayerUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PortalHelperCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("portalhelper")) {

            if ( !(sender instanceof Player) ) {
                sender.sendMessage(ChatUtil.color("&4Only players can execute this command."));

            } else {
                Player player = (Player) sender;

                if (!player.hasPermission("deltacore.portalhelper")) {
                    player.sendMessage(Vars.NO_PERMISSION);
                    return true;

                } else {
                    World.Environment env = player.getLocation().getWorld().getEnvironment();

                    if (env.equals(World.Environment.THE_END)) {
                        player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&cDoesn't make sense to run this command in " + ChatUtil.getColoredWorld(World.Environment.THE_END) + "&c."));

                    } else {

                        int[] playerCoords = PlayerUtil.getCoords(player);

                        player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&5We are assuming you are standing right next to your nether portal."));
                        player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&5If this is false, please run the command again in the correct position."));
                        ChatUtil.sendEmptyLines(1, player);
                        player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7This portal is located in " + ChatUtil.coloredWorld(player) + "&7."));

                        if (env.equals(World.Environment.NORMAL)) {
                            // Player is in the Overworld, calculate coords for a new portal in The Nether
                            player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7Calculating your ideal pairing coordinates to pair this portal with a new one in " + ChatUtil.getColoredWorld(World.Environment.NETHER) + "&7..."));

                            int[] newPortalCoords = calculatePortalCoords(player, World.Environment.NORMAL);

                            ChatUtil.sendEmptyLines(1, player);
                            player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&a&lSUCCESS! &7Place your new portal at &f" + newPortalCoords[0] + "&7, &f" + newPortalCoords[1] + "&7, &f" + newPortalCoords[2] + " &7in " + ChatUtil.getColoredWorld(World.Environment.NETHER) + "&7."));

                        } else {
                            // Player is in the Nether, calculate coords for a new portal in The Overworld
                            player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7Calculating your ideal pairing coordinates to pair this portal with a new one in " + ChatUtil.getColoredWorld(World.Environment.NORMAL) + "&7..."));

                            int[] newPortalCoords = calculatePortalCoords(player, World.Environment.NETHER);

                            ChatUtil.sendEmptyLines(1, player);
                            player.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&a&lSUCCESS! &7Place your new portal at &f" + newPortalCoords[0] + "&7, &f" + newPortalCoords[1] + "&7, &f" + newPortalCoords[2] + " &7in " + ChatUtil.getColoredWorld(World.Environment.NORMAL) + "&7."));
                        }

                    }

                }

            }

            return true;

        }

        return false;
    }

    private int[] calculatePortalCoords(Player player, World.Environment fromEnvironment) {

        if (fromEnvironment.equals(World.Environment.THE_END)) return new int[] { 0, 0, 0 };

        int[] coords = PlayerUtil.getCoords(player);

        if (fromEnvironment.equals(World.Environment.NORMAL)) {
            // Player is in the Overworld
            // Calculate where to place portal in The Nether

            return new int[] { coords[0] / 8, coords[1], coords[2] / 8 };

        } else {
            // Player is in The Nether
            // Calculate where to place portal in the Overworld

            return new int[] { coords[0] * 8, coords[1], coords[2] * 8 };

        }

    }

}
