package dev.bazza.deltacore.commands;

import dev.bazza.deltacore.system.DeltaPlayer;
import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.utils.CommandUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NoteCMD implements CommandExecutor {

    private final Server server;

    public NoteCMD(Server server) {
        this.server = server;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase(Commands.SET_NOTE.getName())) {
            if (commandSender instanceof Player) {
                DeltaPlayer player = server.getPlayer( ((Player) commandSender).getUniqueId() );
                String note = CommandUtil.argsToString(args);
                player.setNote(note);
                player.sendMsg("&aYour note has been changed!");

                return true;
            }
        } else if (command.getName().equalsIgnoreCase(Commands.VIEW_NOTE.getName())) {
            if (commandSender instanceof Player) {
                DeltaPlayer player = server.getPlayer( ((Player) commandSender).getUniqueId() );
                String note = player.getNote();
                player.sendMsg("&7Your note: &r" + note);

                return true;
            }
        }

        return false;
    }
}
