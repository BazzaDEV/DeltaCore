package me.bazzadev.deltacore.managers;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.config.MongoDBConfig;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static com.mongodb.client.model.Updates.set;
import static me.bazzadev.deltacore.utilities.InventoryUtil.playerInventoryToBase64;

public class PlayerDataManager {

    private final MongoDBConfig mongoDBConfig;

    public PlayerDataManager(MongoDBConfig mongoDBConfig) {
        this.mongoDBConfig = mongoDBConfig;
    }

    private MongoClient mongoClient;
    private MongoDatabase database;
    private static MongoCollection<Document> col;

    private final HashMap<UUID, Boolean> afkMap = new HashMap<>();
    private final HashMap<UUID, Boolean> vanishMap = new HashMap<>();
    private final HashMap<UUID, Boolean> staffmodeMap = new HashMap<>();

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
                                                                .append("vanish", false)
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

    }

    public HashMap<UUID, Boolean> getAfkMap() {
        return afkMap;
    }
    public HashMap<UUID, Boolean> getVanishMap() {
        return vanishMap;
    }
    public HashMap<UUID, Boolean> getStaffmodeMap() {
        return staffmodeMap;
    }

    public void loadPlayerData(Document document, UUID uuid) {

        loadStatusData(document, uuid);

        System.out.println("Loaded player data for " + Bukkit.getPlayer(uuid).getName());

        System.out.println(afkMap);
        System.out.println(vanishMap);
        System.out.println(staffmodeMap);

    }


    public void loadData() {

        col.find().forEach((document -> {

            String uuidString = document.getString("uuid");
            UUID uuid = UUID.fromString(uuidString);
            Player player = Bukkit.getPlayer(uuid);

            if (Bukkit.getOnlinePlayers().contains(player)) {

                loadStatusData(document, uuid);

            }

        }));

        System.out.println("Loaded data");

    }

    private void loadStatusData(Document document, UUID uuid) {

        Document status = (Document) document.get("status");

        boolean afk = status.getBoolean("afk");
        afkMap.put(uuid, afk);

        boolean vanish = status.getBoolean("vanish");
        vanishMap.put(uuid, vanish);

        boolean staffmode = status.getBoolean("staffmode");
        staffmodeMap.put(uuid, staffmode);
    }

    public void saveData() {

        col.find().forEach((document -> {

            String uuidString = document.getString("uuid");
            UUID uuid = UUID.fromString(uuidString);

            if (afkMap.containsKey(uuid)) {

                PlayerDataManager.getDatabaseCollection().updateOne(
                        Filters.eq("uuid", uuidString),
                        set("status.afk", afkMap.get(uuid)));

            }

            if (vanishMap.containsKey(uuid)) {

                PlayerDataManager.getDatabaseCollection().updateOne(
                        Filters.eq("uuid", uuidString),
                        set("status.vanish", vanishMap.get(uuid)));

            }

            if (staffmodeMap.containsKey(uuid)) {

                PlayerDataManager.getDatabaseCollection().updateOne(
                        Filters.eq("uuid", uuidString),
                        set("status.staffmode", staffmodeMap.get(uuid)));

            }

        }));

        System.out.println("Saved data");

    }

    public Document[] getPlayerDocumentAndFilter(UUID uuid) {

        Document filter = new Document("uuid", uuid.toString());
        return new Document[] {filter, PlayerDataManager.getDatabaseCollection().find(filter).first()};

    }

    public Document getPlayerDocument(UUID uuid) {

        Document filter = new Document("uuid", uuid.toString());
        return PlayerDataManager.getDatabaseCollection().find(filter).first();

    }

}
