package dev.bazza.deltacore.afk;

import dev.bazza.deltacore.commands.Commands;
import dev.bazza.deltacore.utils.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AfkCMD implements CommandExecutor {

    private final AFKManager afkManager;

    public AfkCMD(AFKManager afkManager) {
        this.afkManager = afkManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase(Commands.AFK.getName())) {
            if (commandSender instanceof Player) {
                Player p = (Player) commandSender;
                afkManager.toggleAFK(p, true, true);

                return true;

            } else { // Console is sending command
                commandSender.sendMessage(ChatUtil.CONSOLE_CANNOT_EXECUTE());

            }
        }

        return false;
    }
}
