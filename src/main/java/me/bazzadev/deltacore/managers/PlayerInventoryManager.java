package me.bazzadev.deltacore.managers;

import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.utilities.PlayerDataManager;
import me.bazzadev.deltacore.utilities.Vars;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static me.bazzadev.deltacore.utilities.InventoryUtil.itemStackArrayFromBase64;
import static me.bazzadev.deltacore.utilities.InventoryUtil.playerInventoryToBase64;

public class PlayerInventoryManager {

    public static final String TEST_BASE_PATH = "test.inventory";
    public static final String[] TEST_BASE_PATH_ARR = { "test", "inventory" };

    private final PlayerDataManager playerDataManager;

    public PlayerInventoryManager(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }


    public void saveContents(Player player, String basePath) {

        PlayerInventory playerInv = player.getInventory();
        String playerUUIDString = player.getUniqueId().toString();

        String[] playerInventoryToBase64 = playerInventoryToBase64(playerInv);

        DeltaCore.newChain()
                .async(() -> PlayerDataManager.getDatabaseCollection().updateOne(
                        Filters.eq("uuid", playerUUIDString),
                        combine(set(basePath + ".inv", playerInventoryToBase64[0]),
                                set(basePath + ".armor", playerInventoryToBase64[1]))))
                .execute(() -> System.out.println("Successfully saved inventory contents for player " + player.getName()));

    }

    public void loadContents(Player player, String[] basePath) {

        String playerUUIDString = player.getUniqueId().toString();

        Document filter = new Document("uuid", playerUUIDString);

        DeltaCore.newChain()
                .asyncFirst(() -> {
                    Document playerdata = PlayerDataManager.getDatabaseCollection().find(filter).first();
                    Document inventory = fileFromPath(playerdata, basePath);

                    String invBase64 = inventory.getString("inv");
                    String armorBase64 = inventory.getString("armor");

                    return new String[]{invBase64, armorBase64};
                })
                .syncLast((invContents) -> {
                    try {

                        ItemStack[] inv = itemStackArrayFromBase64(invContents[0]);
                        ItemStack[] armor = itemStackArrayFromBase64(invContents[1]);

                        player.getInventory().setContents(inv);
                        player.getInventory().setArmorContents(armor);

                    } catch (IOException e) {
                        player.sendMessage(Vars.PLUGIN_PREFIX + "An error occurred.");
                    }
                })
                .execute(() -> System.out.println("Successfully loaded inventory contents for player " + player.getName()));




        /*Document playerData = PlayerDataManager.getDatabaseCollection().find(filter).first();

        Document inventory = playerData;

        for (int i=0; i < basePath.length; i++) {

            inventory = (Document) inventory.get(basePath[i]);

        }

        String invBase64 = inventory.getString("inv");
        String armorBase64 = inventory.getString("armor");

        try {

            ItemStack[] inv = itemStackArrayFromBase64(invBase64);
            ItemStack[] armor = itemStackArrayFromBase64(armorBase64);

            player.getInventory().setContents(inv);
            player.getInventory().setArmorContents(armor);

        } catch (IOException e) {
            player.sendMessage(Vars.PLUGIN_PREFIX + "An error occurred.");
        }*/

    }

    public Document fileFromPath(Document playerData, String[] basePath) {

        Document inventory = playerData;

        for (int i=0; i < basePath.length; i++) {

            inventory = (Document) inventory.get(basePath[i]);

        }

        return inventory;

    }

}
