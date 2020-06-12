package me.bazzadev.deltacore.inventory;

import me.bazzadev.deltacore.config.PlayerDataConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;
import java.util.UUID;

import static me.bazzadev.deltacore.utilities.InventoryUtil.itemStackArrayFromBase64;
import static me.bazzadev.deltacore.utilities.InventoryUtil.playerInventoryToBase64;

public class PlayerInventoryManager {

    private final PlayerDataConfig playerDataConfig;

    public PlayerInventoryManager(PlayerDataConfig playerDataConfig) {
        this.playerDataConfig = playerDataConfig;
    }

    public void saveContents(Player player) {

        PlayerInventory playerInv = player.getInventory();

        String[] playerInventoryToBase64 = playerInventoryToBase64(playerInv);
        String pathtoInv = (player.getUniqueId().toString()) + ".inventory" + ".inv";
        String pathtoArmor = (player.getUniqueId().toString()) + ".inventory" + ".armor";

        playerDataConfig.get().set(pathtoInv, playerInventoryToBase64[0]);
        playerDataConfig.get().set(pathtoArmor, playerInventoryToBase64[1]);

        playerDataConfig.save();

    }

    public void loadContents(Player player) {

        String pathtoInv = (player.getUniqueId().toString()) + ".inventory" + ".inv";
        String pathtoArmor = (player.getUniqueId().toString()) + ".inventory" + ".armor";

        try {

            ItemStack[] inv = itemStackArrayFromBase64( playerDataConfig.get().getString(pathtoInv) );
            ItemStack[] armor = itemStackArrayFromBase64( playerDataConfig.get().getString(pathtoArmor) );

            player.getInventory().setContents(inv);
            player.getInventory().setArmorContents(armor);

        } catch (IOException e) {
            player.sendMessage("An error occurred.");
        }



    }

}
