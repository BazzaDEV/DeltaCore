package dev.bazza.deltacore.afk;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import org.bukkit.entity.Player;

@CommandAlias("afk")
public class AfkCMD extends BaseCommand {

    private final AFKManager afkManager;

    public AfkCMD(AFKManager afkManager) {
        this.afkManager = afkManager;
    }

    @Default
    public void onAfk(Player p) {
        afkManager.toggleAFK(p, true, true);
    }

}
