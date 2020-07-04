package me.bazzadev.deltacore.commands;

import me.bazzadev.deltacore.managers.PlayerInventoryManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoadInventoryCMD implements CommandExecutor {

    private final PlayerInventoryManager playerInventoryManager;

    public LoadInventoryCMD(PlayerInventoryManager playerInventoryManager) {
        this.playerInventoryManager = playerInventoryManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("loadinv")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (player.hasPermission("deltacore.loadinv.self")) {
                    playerInventoryManager.loadContents(player, PlayerInventoryManager.TEST_BASE_PATH_ARR);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Vars.PLUGIN_PREFIX + "&7Your inventory has been &arestored&7."));

                } else {
                    player.sendMessage(Vars.NO_PERMISSION);
                }

                return true;

            }


        }

        return false;
    }
}
