package dev.bazza.deltacore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import dev.bazza.deltacore.system.models.User;
import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.utils.Util;
import org.bukkit.entity.Player;

@CommandAlias("note")
public class NoteCMD extends BaseCommand {

    private final Server server;

    public NoteCMD(Server server) {
        this.server = server;
    }

    @Default
    @Subcommand("view")
    @Description("Shows you your personal note.")
    public void onView(Player p) {
        User player = server.getOnlineUser(p.getUniqueId());

        String note = player.getNote();
        player.sendMsg("&7Your note: &r" + note);
    }

    @Subcommand("set")
    @Description("Set your personal note.")
    public void onSet(Player p, String[] args) {
        User player = server.getOnlineUser(p.getUniqueId());

        String note = Util.argsToString(args);
        player.setNote(note);
        player.sendMsg("&aYour note has been changed!");
    }

}
