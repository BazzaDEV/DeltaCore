package me.bazzadev.deltacore.staffmode;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.afk.AFKManager;
import me.bazzadev.deltacore.utilities.ColorUtil;
import me.bazzadev.deltacore.utilities.ItemBuilder;
import me.bazzadev.deltacore.utilities.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class StaffModeInventory{

    private Inventory invPlayersList;
    public static final String INV_TITLE = ColorUtil.translate("&dSM &7&l>> &fOnline Players");

    public Inventory createGUI() {
        invPlayersList = Bukkit.createInventory(null, 27, INV_TITLE);
        initializePlayerHeads();
        return invPlayersList;
    }

    private void initializePlayerHeads() {

        int index = 0;

        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            invPlayersList.setItem(index, customGUIHead(p));
            index += 1;
        }

    }

    public static ItemStack customGUIHead(Player player) {
        return new ItemBuilder(SkullCreator.getPlayerHead(player))
                    .setName(ColorUtil.translate(DeltaCore.getChat().getPlayerPrefix(player) + player.getName()))
                    .addLoreLine(ColorUtil.translate("&7UUID: &8" + player.getUniqueId().toString()))
                    .addLoreLine(ColorUtil.translate("&7Rank: " + DeltaCore.getChat().getPrimaryGroup(player)))
                    .addLoreLine(ColorUtil.translate("&7AFK? " + coloredAFKStatus(player)))
                    .addLoreLine(ColorUtil.translate("&7Currently in: " + coloredWorld(player)))
                    .toItemStack();
    }

    private static String coloredAFKStatus(Player player) {

        if (AFKManager.getStatus(player)) {
            return ColorUtil.translate("&a&l✔");
        }

        return ColorUtil.translate("&c&l✘");

    }

    private static String coloredWorld(Player player) {
        World.Environment env = player.getWorld().getEnvironment();
        if (env==World.Environment.NORMAL) {
            return ColorUtil.translate("&2Overworld");
        } else if (env== World.Environment.NETHER) {
            return ColorUtil.translate("&4Nether");
        } else {
            return ColorUtil.translate("&5The End");
        }

    }









}
