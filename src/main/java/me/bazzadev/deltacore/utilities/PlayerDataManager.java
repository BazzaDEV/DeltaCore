package me.bazzadev.deltacore.utilities;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.bazzadev.deltacore.config.MongoDBConfig;
import org.bson.Document;
import org.bukkit.entity.Player;

import static me.bazzadev.deltacore.utilities.InventoryUtil.playerInventoryToBase64;

public class PlayerDataManager {

    private final MongoDBConfig mongoDBConfig;

    public PlayerDataManager(MongoDBConfig mongoDBConfig) {
        this.mongoDBConfig = mongoDBConfig;
    }

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> col;

    public void initialize() {
        mongoClient = MongoClients.create(mongoDBConfig.get().getString("connection-string"));
        database = mongoClient.getDatabase(mongoDBConfig.get().getString("database"));
        col = database.getCollection(mongoDBConfig.get().getString("collections.player-data"));
    }

    public MongoCollection<Document> getDatabaseCollection() {
        return col;
    }

    public void initializePlayer(Player player) {

        String[] playerInventoryToBase64 = playerInventoryToBase64(player.getInventory());

        Document playerData = new Document("uuid", player.getUniqueId().toString())
                                           .append("IGN", player.getName())
                                           .append("status",
                                                   new Document("afk", false)
                                                        .append("staffmode", false))
                                           .append("inventory",
                                                   new Document("inv", playerInventoryToBase64[0])
                                                        .append("armor", playerInventoryToBase64[1]));


        col.insertOne(playerData);

    }
}
