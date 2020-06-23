package me.bazzadev.deltacore.staffmode;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.utilities.ColorUtil;
import me.bazzadev.deltacore.utilities.ItemBuilder;
import me.bazzadev.deltacore.utilities.SkullCreator;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerActionsInventory {

    public static final int SLOT_PLAYER_HEAD = 4;
    public static final int SLOT_VIEW_INVENTORY = 19;
    public static final int SLOT_PUNISHMENTS = 20;
    public static final int SLOT_GO_BACK = 36;

    private final Inventory inv;
    private final Player player;
    public static final String INV_TITLE = ColorUtil.translate("&fPlayer: ");

    public PlayerActionsInventory(Player player) {
        this.player = player;
        inv = Bukkit.createInventory(null, 45, ColorUtil.translate(INV_TITLE + DeltaCore.getChat().getPlayerPrefix(this.player) + this.player.getName()));
        initializeItems();
    }

    private void initializeItems() {
        inv.setItem(SLOT_PLAYER_HEAD, SkullCreator.getHeadWithPlayerData(player));
        inv.setItem(SLOT_GO_BACK, Vars.GO_BACK);
        inv.setItem(SLOT_VIEW_INVENTORY, new ItemBuilder(Material.CHEST)
                                        .setName(ColorUtil.translate("&eView Inventory"))
                                        .toItemStack());
        inv.setItem(SLOT_PUNISHMENTS, new ItemBuilder(Material.TNT)
                                        .setName(ColorUtil.translate("&cPunishments"))
                                        .addLoreLine(ColorUtil.translate("&7Click to view punishment options for this player."))
                                        .toItemStack());

    }

    public Inventory getBukkitInventory() {
        return inv;
    }

}
