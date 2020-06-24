package me.bazzadev.deltacore.staffmode;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class StaffModeItems {

    public static final ItemStack viewPlayerList = new ItemBuilder(Material.COMPASS)
                                                        .setName(ChatUtil.color("&6View Player List"))
                                                        .addLoreLine(ChatUtil.color("&7Right-click to &aopen &7the menu."))
                                                        .removeNBTTag(ItemFlag.HIDE_ATTRIBUTES)
                                                        .toItemStack();

}
