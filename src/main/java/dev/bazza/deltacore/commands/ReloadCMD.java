package dev.bazza.deltacore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.utils.Util;
import org.bukkit.command.CommandSender;

@CommandAlias("%deltacore")
@Subcommand("reload")
public class ReloadCMD extends BaseCommand {

    private final DeltaCore plugin;

    public ReloadCMD(DeltaCore plugin) {
        this.plugin = plugin;
    }

    @Default
    public void onReload(CommandSender sender) {
        plugin.reloadPlugin();
        Util.msg(sender,"&7The plugin has been reloaded successfully.");
    }

}
