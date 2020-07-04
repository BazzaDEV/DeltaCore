package me.bazzadev.deltacore.commands;

import me.bazzadev.deltacore.managers.StaffModeManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class StaffModeCMD implements CommandExecutor {

    private final StaffModeManager staffModeManager;

    public StaffModeCMD(StaffModeManager staffModeManager) {
        this.staffModeManager = staffModeManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("staffmode")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (player.hasPermission("deltacore.staffmode")) {
                    if (args.length==0) {

                        staffModeManager.toggle(player);
                        return true;

                    } else if (args[0].equalsIgnoreCase("tppos")) {

                        Player p = Bukkit.getPlayer(args[1]);

                        World world = Bukkit.getWorld(args[2]);
                        double x = Double.parseDouble(args[3]);
                        double y = Double.parseDouble(args[4]);
                        double z = Double.parseDouble(args[5]);

                        Location location = new Location(world, x, y, z);

                        p.teleport(location);

                        ChatUtil.sendEmptyLines(100, player);
                        p.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7You have been teleported back to your original location."));

                        return true;
                    }

                } else {
                    player.sendMessage(Vars.NO_PERMISSION);
                }

                return true;

            }

        }

        return false;
    }

}
