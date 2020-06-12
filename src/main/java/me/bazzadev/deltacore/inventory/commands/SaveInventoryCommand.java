package me.bazzadev.deltacore.inventory.commands;

import me.bazzadev.deltacore.inventory.PlayerInventoryManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SaveInventoryCommand implements CommandExecutor {

    private final PlayerInventoryManager playerInventoryManager;

    public SaveInventoryCommand(PlayerInventoryManager playerInventoryManager) {
        this.playerInventoryManager = playerInventoryManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("saveinv")) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (player.hasPermission("staffmode.saveinv.self")) {
                    playerInventoryManager.saveContents(player);

                } else {
                    player.sendMessage(ChatColor.RED + "No permission, bud.");
                }

                return true;

            }

        }

        return false;
    }

}
