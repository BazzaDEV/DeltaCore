package me.bazzadev.deltacore.utilities;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.config.MongoDBConfig;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static me.bazzadev.deltacore.utilities.InventoryUtil.playerInventoryToBase64;

public class PlayerDataManager {

    private final MongoDBConfig mongoDBConfig;

    public PlayerDataManager(MongoDBConfig mongoDBConfig) {
        this.mongoDBConfig = mongoDBConfig;
    }

    private MongoClient mongoClient;
    private MongoDatabase database;
    private static MongoCollection<Document> col;

    public void initialize() {

        try {
            mongoClient = MongoClients.create(mongoDBConfig.get().getString("connection-string"));
            database = mongoClient.getDatabase(mongoDBConfig.get().getString("database"));
            col = database.getCollection(mongoDBConfig.get().getString("collections.player-data"));
        } catch (NullPointerException e) {
            System.out.println("You need to enter your MongoDB credentials in plugins/DeltaCore/mongodb.yml");
        }

    }

    public static MongoCollection<Document> getDatabaseCollection() {
        return col;
    }

    public void initializePlayer(Player player) {

        String[] playerInventoryToBase64 = playerInventoryToBase64(player.getInventory());
        String uuid = player.getUniqueId().toString();
        String ign = player.getName();
        Location location = player.getLocation();
        String worldName = location.getWorld().getName();
        int[] coords = { location.getBlockX(), location.getBlockY(), location.getBlockZ() };


        DeltaCore.newChain()
                .asyncFirst(() -> new Document("uuid", uuid)
                                                .append("test",
                                                        new Document("inventory",
                                                                new Document("inv", playerInventoryToBase64[0])
                                                                        .append("armor", playerInventoryToBase64[1])))
                                                .append("IGN", ign)
                                                .append("status",
                                                        new Document("afk", false)
                                                                .append("staffmode", false))
                                                .append("staffmode-data",
                                                        new Document("originallocation",
                                                                new Document("World", worldName)
                                                                        .append("X", coords[0])
                                                                        .append("Y", coords[1])
                                                                        .append("Z", coords[2])
                                                                .append("survival-inventory",
                                                                        new Document("inv", playerInventoryToBase64[0])
                                                                                .append("armor", playerInventoryToBase64[1])))
                                                .append("last-death",
                                                        new Document("inventory",
                                                                new Document("inv", playerInventoryToBase64[0])
                                                                        .append("armor", playerInventoryToBase64[1])))))
                .asyncLast((playerData) -> col.insertOne(playerData))
                .execute();


        // Same task as above, but does not run async obviously.


//        Document playerData = new Document("uuid", player.getUniqueId().toString())
//                                           .append("test",
//                                                   new Document("inventory",
//                                                           new Document("inv", playerInventoryToBase64[0])
//                                                                   .append("armor", playerInventoryToBase64[1])))
//                                           .append("IGN", player.getName())
//                                           .append("status",
//                                                   new Document("afk", false)
//                                                        .append("staffmode", false))
//                                           .append("staffmode-data",
//                                                   new Document("originallocation",
//                                                           new Document("World", player.getLocation().getWorld().getName())
//                                                                .append("X", player.getLocation().getBlockX())
//                                                                .append("Y", player.getLocation().getBlockY())
//                                                                .append("Z", player.getLocation().getBlockZ()))
//                                                   .append("survival-inventory",
//                                                           new Document("inv", playerInventoryToBase64[0])
//                                                                   .append("armor", playerInventoryToBase64[1])))
//                                           .append("last-death",
//                                                   new Document("inventory",
//                                                           new Document("inv", playerInventoryToBase64[0])
//                                                                   .append("armor", playerInventoryToBase64[1])));
//
//        col.insertOne(playerData);

    }

    public Document getPlayerDocument(Player player) {

        String playerUUIDString = player.getUniqueId().toString();
        Document filter = new Document("uuid", playerUUIDString);

        return col.find(filter).first();
    }

}
