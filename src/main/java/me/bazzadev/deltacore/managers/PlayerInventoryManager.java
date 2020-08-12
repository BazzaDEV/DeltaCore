package me.bazzadev.deltacore.managers;

import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import static me.bazzadev.deltacore.utilities.InventoryUtil.itemStackArrayFromBase64;
import static me.bazzadev.deltacore.utilities.InventoryUtil.playerInventoryToBase64;

public class PlayerInventoryManager {

    public static final String TEST_BASE_PATH = "test.inventory";
    public static final String[] TEST_BASE_PATH_ARR = { "test", "inventory" };

    private void setInventoryContents(Player player, String[] playerInventoryBase64) {
        try {

            ItemStack[] inv = itemStackArrayFromBase64(playerInventoryBase64[0]);
            ItemStack[] armor = itemStackArrayFromBase64(playerInventoryBase64[1]);

            player.getInventory().setContents(inv);
            player.getInventory().setArmorContents(armor);

        } catch (IOException e) {
            player.sendMessage(ChatUtil.color(Vars.ERROR_PREFIX + "&cAn error occurred while attempting to restore your inventory."));
        }
    }

    public void saveContents(Player player, HashMap<UUID, String[]> invMap) {

        PlayerInventory playerInv = player.getInventory();
        UUID uuid = player.getUniqueId();
        String[] playerInventoryToBase64 = playerInventoryToBase64(playerInv);

        invMap.put(uuid, playerInventoryToBase64);
        System.out.println("Successfully saved inventory contents for player " + player.getName());

    }

    @Deprecated
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

    public void loadContents(Player player, HashMap<UUID, String[]> invMap) {

        UUID uuid = player.getUniqueId();
        String[] playerInventoryBase64 = invMap.get(uuid);

        setInventoryContents(player, playerInventoryBase64);

    }

    @Deprecated
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
                    setInventoryContents(player, invContents);
                })
                .execute(() -> System.out.println("Successfully loaded inventory contents for player " + player.getName()));

    }

    public static Document fileFromPath(Document playerData, String[] basePath) {

        Document inventory = playerData;

        for (int i=0; i < basePath.length; i++) {

            inventory = (Document) inventory.get(basePath[i]);

        }

        return inventory;

    }

}
