package me.bazzadev.deltacore.utilities;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Vars {

    public static final String PLUGIN_PREFIX = ChatUtil.color("&8&l" + '▍' + " &c&lDelta&f&lCore &8▎ &r");
    public static final String ERROR_PREFIX = ChatUtil.color("&8&l" + '▍' + " &4&lERROR &8▎ &r");
    public static final String VANISH_PREFIX = ChatUtil.color("&8&l" + '▍' + " &d&lVanish &8▎ &r");

    public static final String NO_PERMISSION = PLUGIN_PREFIX + ChatColor.RED + "No permission, bud.";

    public static final String SHULKER_BOX_SILENT_MODE_TITLE = ChatUtil.color("&fShulker Box &8[&7Silent Mode&8]");
    public static final String CHEST_SILENT_MODE_TITLE = ChatUtil.color("&fChest &8[&7Silent Mode&8]");

    public static final ItemStack GO_BACK = new ItemBuilder(SkullCreator.getCustomHeadfromURL("http://textures.minecraft.net/texture/4c301a17c955807d89f9c72a19207d1393b8c58c4e6e420f714f696a87fdd"))
                                                .setName(ChatUtil.color("&7Go Back"))
                                                .removeNBTTag(ItemFlag.HIDE_ATTRIBUTES)
                                                .toItemStack();

}
