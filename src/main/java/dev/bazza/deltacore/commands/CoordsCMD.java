package dev.bazza.deltacore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.system.models.User;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandAlias("coords")
public class CoordsCMD extends BaseCommand {

    private final Server server;

    public CoordsCMD(Server server) { this.server = server; }

    @Default
    @Subcommand("short")
    @Description("Sends a shortened version of your coordinates to the chat.")
    public void onShort(Player p) {
        UUID uuid = p.getUniqueId();
        User player = server.getOnlineUser(uuid);

        Location loc = p.getLocation();
        int x = loc.getBlockX();
        // int y = loc.getBlockY();
        int z = loc.getBlockZ();

        player.sendChat("&fX: &6&n" + x + "&r &fZ:&6 &n" + z);


    }

}
