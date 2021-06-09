package dev.bazza.deltacore.data.database.local;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.system.models.User;
import dev.bazza.deltacore.system.models.roles.OfflineRole;
import dev.bazza.deltacore.utils.Util;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class JsonDBManager extends LocalDatabaseManager {

    public JsonDBManager(DeltaCore plugin) {
        super(plugin);
    }

    private static final String FILENAME = "db.json";
    private File file;

    @Override
    public void initialize() {
        file = new File(plugin.getDataFolder(), FILENAME);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(FILENAME, false);
        }

    }

    @Override
    public void load() {

    }

    @Override
    public void sync() {
        try {
            HashMap<UUID, User> allDatabaseUsers = getAllUsersFromDatabase();
            allDatabaseUsers.putAll(userCache);

            Writer writer = new FileWriter(file, false);
            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setIndent("  ");
            jsonWriter.setLenient(true);

            jsonWriter
                    .beginObject()
                    .name("players")
                    .beginArray();

            Gson gson = new GsonBuilder()
                    .addSerializationExclusionStrategy(EXCLUSION_STRATEGY)
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .serializeNulls()
                    .create();



            allDatabaseUsers.forEach(((uuid, user) -> {

                try {
                    String serializedUser = gson.toJson(user);

                    jsonWriter
                            .beginObject()
                            .name(uuid.toString())
                            .jsonValue(serializedUser)
                            .endObject();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }));

            jsonWriter.endArray()
                    .endObject();

            writer.flush();
            writer.close();

            jsonWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean isUser(UUID uuid) {
        return (userCache.containsKey(uuid) || getAllUsersFromDatabase().containsKey(uuid));
    }

    @Override
    public User getUser(UUID uuid) {
        // Check if user is stored in cache
        if (userCache.containsKey(uuid))
            return userCache.get(uuid);

        // User is not cached; check the database
        else if (isUser(uuid))
            return getAllUsersFromDatabase().get(uuid);

        // User data not found, return null;
        else
            return null;

    }

    public HashMap<UUID, User> getAllUsersFromDatabase() {
        HashMap<UUID, User> playerMap = new HashMap<>();

        String testJsonStr =
                "{\n" +
                "  \"players\": [\n" +
                "    {\n" +
                "      \"2e8770bf-1478-4f75-a80b-407d2978283f\": {\n" +
                "  \"uuid\": \"2e8770bf-1478-4f75-a80b-407d2978283f\",\n" +
                "  \"IGN\": \"BazzaDEV\",\n" +
                "  \"afk\": false,\n" +
                "  \"lastActiveTime\": 1623213602909,\n" +
                "  \"note\": null\n" +
                "}\n" +
                "    },\n" +
                "    {\n" +
                "      \"cf134715-cd67-4214-8cd2-ccbe1f5d70e5\": {\n" +
                "  \"uuid\": \"cf134715-cd67-4214-8cd2-ccbe1f5d70e5\",\n" +
                "  \"IGN\": \"MeatSceptre\",\n" +
                "  \"afk\": false,\n" +
                "  \"lastActiveTime\": 1623213333667,\n" +
                "  \"note\": null\n" +
                "}\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        try {
            // StringReader reader = new StringReader(
            FileReader reader = new FileReader(file);
            JsonReader jsonReader = new JsonReader(reader);
            jsonReader.setLenient(true);

            Gson gson = new GsonBuilder()
                    .addSerializationExclusionStrategy(EXCLUSION_STRATEGY)
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .serializeNulls()
                    .create();

            if (jsonReader.peek().equals(JsonToken.BEGIN_OBJECT)) {
                jsonReader.beginObject();

                if (jsonReader.peek().equals(JsonToken.NAME)) {
                    jsonReader.nextName();

                    if (jsonReader.peek().equals(JsonToken.BEGIN_ARRAY)) {
                        jsonReader.beginArray();

                        while (jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            jsonReader.nextName();

                            User user = gson.fromJson(jsonReader, User.class);
                            user.setRole(new OfflineRole());
                            playerMap.put(user.getUuid(), user);

                            jsonReader.endObject();
                        }

                        jsonReader.endArray();

                        jsonReader.endObject();
                    }
                }
            }

            reader.close();
            jsonReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return playerMap;

    }
}
