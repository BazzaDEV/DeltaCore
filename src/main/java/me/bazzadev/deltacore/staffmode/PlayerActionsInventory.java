package me.bazzadev.deltacore.staffmode;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.ItemBuilder;
import me.bazzadev.deltacore.utilities.SkullCreator;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class PlayerActionsInventory {

    public static final int SLOT_PLAYER_HEAD = 4;
    public static final int SLOT_VIEW_INVENTORY = 19;
    public static final int SLOT_VIEW_ENDERCHEST = 20;
    public static final int SLOT_PUNISHMENTS = 21;
    public static final int SLOT_TELEPORT_TO_PLAYER = 22;
    public static final int SLOT_GO_BACK = 36;

    private final Inventory inv;
    private final Player player;

    public static final String INV_TITLE = ChatUtil.color("&fPlayer: ");

    public PlayerActionsInventory(Player player) {

        this.player = player;
        inv = Bukkit.createInventory(null, 45, ChatUtil.color(INV_TITLE + ChatUtil.playerNameWithPrefix(player)));
        initializeItems();

    }

    private void initializeItems() {

        inv.setItem(SLOT_PLAYER_HEAD, SkullCreator.getHeadWithPlayerData(player));
        inv.setItem(SLOT_GO_BACK, Vars.GO_BACK);
        inv.setItem(SLOT_VIEW_INVENTORY, new ItemBuilder(Material.CHEST)
                                        .setName(ChatUtil.color("&eView Inventory"))
                                        .addLoreLine(ChatUtil.color("&7Click to view the player's inventory contents."))
                                        .addLoreLine(ChatUtil.color("&7Support for viewing armor and off-hand item &a&lCOMING SOON&7!"))
                                        .toItemStack());
        inv.setItem(SLOT_VIEW_ENDERCHEST, new ItemBuilder(Material.ENDER_CHEST)
                                        .setName(ChatUtil.color("&5View Ender Chest"))
                                        .addLoreLine(ChatUtil.color("&7Click to view the player's ender chest contents."))
                                        .toItemStack());
        inv.setItem(SLOT_PUNISHMENTS, new ItemBuilder(Material.TNT)
                                        .setName(ChatUtil.color("&cPunishments"))
                                        .addLoreLine(ChatUtil.color("&7Click to view punishment options for this player."))
                                        .toItemStack());
        inv.setItem(SLOT_TELEPORT_TO_PLAYER, new ItemBuilder(Material.ENDER_PEARL)
                                        .setName(ChatUtil.color("&bTeleport to Player"))
                                        .addLoreLine(ChatUtil.color("&7Click to teleport to this player."))
                                        .addLoreLine(ChatUtil.color("&7You will be &d&lVANISHED &7automagically."))
                                        .toItemStack());

    }

    public Inventory getBukkitInventory() {
        return inv;
    }

    public static Player getPlayerFromGUIHead(Player player) {

        ItemStack targetHead = player.getOpenInventory().getTopInventory().getItem(PlayerActionsInventory.SLOT_PLAYER_HEAD);

        UUID targetUUID = UUID.fromString(ChatColor.stripColor(targetHead.getItemMeta().getLore().get(0)).replace("UUID: ", "").trim());
        return Bukkit.getPlayer(targetUUID);

    }

}
