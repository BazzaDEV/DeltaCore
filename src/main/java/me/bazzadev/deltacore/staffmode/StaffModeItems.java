package me.bazzadev.deltacore.staffmode;

import me.bazzadev.deltacore.utilities.ColorUtil;
import me.bazzadev.deltacore.utilities.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StaffModeItems {

    public static final ItemStack viewPlayerList = new ItemBuilder(Material.COMPASS)
                                                        .setName(ColorUtil.translate("&6View Player List"))
                                                        .addLoreLine(ColorUtil.translate("&7Right-click to &aopen &7the menu."))
                                                        .toItemStack();

}
