package dev.bazza.deltacore.data.database.local;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dev.bazza.deltacore.DeltaCore;
import dev.bazza.deltacore.system.DeltaPlayer;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class JsonDBManager extends LocalDatabaseManager {

    public JsonDBManager(DeltaCore plugin) {
        super(plugin);
    }

    private static final String FILENAME = "db.json";

    /************************************************************************/

    private static final String IGN_PATH = ".IGN";
    private static final String AFK_STATUS_PATH = ".status.afk";
    private static final String NOTE_PATH = ".note";

    /************************************************************************/

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
    public void save() {

    }

    @Override
    public boolean isPlayer(UUID uuid) {
        return getAllPlayers().containsKey(uuid);
    }

    @Override
    public DeltaPlayer createPlayerFromDB(UUID uuid) {
        HashMap<UUID, DeltaPlayer> playerMap = getAllPlayers();

        if (playerMap.containsKey(uuid)) { // An entry for this UUID exists in the database
            DeltaPlayer p = playerMap.get(uuid);
            return new DeltaPlayer(p.getUuid(), p.getIGN(), p.isAfk(), new Date().getTime(), p.getNote());

        } else { // No entry exists for this UUID
            return null;
            
        }
    }

    @Override
    public void updatePlayer(DeltaPlayer player) {
        try {
            HashMap<UUID, DeltaPlayer> playerMap = getAllPlayers();
            playerMap.put(player.getUuid(), player);

            Writer writer = new FileWriter(file, false);
            JsonWriter jsonWriter = new JsonWriter(writer);
            jsonWriter.setIndent("  ");

            jsonWriter
                    .beginObject()
                    .name("players")
                    .beginArray();

            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .disableHtmlEscaping()
                    .serializeNulls()
                    .create();

            playerMap.forEach(((uuid, deltaPlayer) -> {
                try {
                    String playerJson = gson.toJson(deltaPlayer);
                    // System.out.println(playerJson);

                    jsonWriter
                            .beginObject()
                            .name(uuid.toString())
                            .jsonValue(playerJson)
                            .endObject();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }));

            jsonWriter.endArray()
                    .endObject();

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<UUID, DeltaPlayer> getAllPlayers() {
        HashMap<UUID, DeltaPlayer> playerMap = new HashMap<>();

        try {
            FileReader reader = new FileReader(file);
            JsonReader jsonReader = new JsonReader(reader);

            Gson gson = new GsonBuilder()
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

                            DeltaPlayer player = gson.fromJson(jsonReader, DeltaPlayer.class);
                            playerMap.put(player.getUuid(), player);

                            jsonReader.endObject();
                        }

                        jsonReader.endArray();

                        jsonReader.endObject();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return playerMap;

    }
}
