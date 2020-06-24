package me.bazzadev.deltacore.inventory.commands;

import me.bazzadev.deltacore.inventory.PlayerInventoryManager;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RestoreInventoryFromDeathCommand implements CommandExecutor {

    public static final String BASE_PATH = "last-death.inventory";
    public static final String[] BASE_PATH_ARR = { "last-death", "inventory" };

    private final PlayerInventoryManager playerInventoryManager;

    public RestoreInventoryFromDeathCommand(PlayerInventoryManager playerInventoryManager) {
        this.playerInventoryManager = playerInventoryManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("restoreinv")) {

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("deltacore.restoreinv.fromdeath")) {
                    player.sendMessage(Vars.NO_PERMISSION);
                    return true;
                }
            }

            if (args.length==2 && args[0].equalsIgnoreCase("fromdeath")) {
                restoreDeathInventory(args[1]);
                return true;
            }

        }

        return false;
    }

    private void restoreDeathInventory(String targetName) {

        Player target = Bukkit.getPlayer(targetName);
        playerInventoryManager.loadContents(target, BASE_PATH_ARR);

        target.sendMessage(ChatUtil.color(Vars.PLUGIN_PREFIX + "&7Your inventory has been &a&lRESTORED &7to its pre-death contents."));
    }

}
