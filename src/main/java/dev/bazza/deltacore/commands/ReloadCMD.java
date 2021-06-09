package dev.bazza.deltacore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("%deltacore")
@Subcommand("reload")
@CommandPermission("deltacore.reload")
public class ReloadCMD extends BaseCommand {

    private final DeltaCore plugin;
    private final Server server;

    public ReloadCMD(DeltaCore plugin, Server server) {
        this.plugin = plugin;
        this.server = server;
    }

    @Default
    public void onReload(CommandSender sender) {
        Player p = (Player) sender;
        plugin.reloadPlugin();
        Util.msg(sender,"&7The plugin has been reloaded successfully.");
    }

}
