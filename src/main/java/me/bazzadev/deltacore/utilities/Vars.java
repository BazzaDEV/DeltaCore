package me.bazzadev.deltacore.utilities;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Vars {

    public static final String PLUGIN_PREFIX = ChatColor.translateAlternateColorCodes('&', "&8&l" + '▍' + " &c&lDelta&f&lCore &8▎ &r");
    public static final String NO_PERMISSION = Vars.PLUGIN_PREFIX + ChatColor.RED + "No permission, bud.";
    public static final String SLEEP_PREFIX = ChatUtil.color("&8[&dSleep&8] &r");

    public static final ItemStack GO_BACK = new ItemBuilder(SkullCreator.getCustomHeadfromURL("http://textures.minecraft.net/texture/4c301a17c955807d89f9c72a19207d1393b8c58c4e6e420f714f696a87fdd"))
                                                .setName(ChatUtil.color("&7Go Back"))
                                                .removeNBTTag(ItemFlag.HIDE_ATTRIBUTES)
                                                .toItemStack();

}
