package me.bazzadev.deltacore.managers;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.tasks.SpawnParticleTask;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class ItemSearchManager {

    private final DeltaCore plugin;

    private Material material;
    private Player player;
    private Chunk[] nearbyChunks;
    private HashMap<BlockState, Location> results;

    public ItemSearchManager(DeltaCore plugin) {
        this.plugin = plugin;
    }

    public void newSearch(Material material, Player player) {

        this.material = material;
        this.player = player;

        results = new HashMap<>();

        nearbyChunks = getNearbyChunks();

        for (Chunk c : nearbyChunks) {
            player.sendMessage(c.toString());

        }

        searchContainers();

        results.forEach(((blockState, location) -> {

            new SpawnParticleTask(player, Particle.FLASH, location).runTaskTimer(plugin, 0, 10);

        }));

        player.sendMessage("Done. Found " + results.size() + " containers with the item " + material.toString());

    }

    private Chunk[] getNearbyChunks() {

        Chunk centerChunk = player.getChunk();
        World world = centerChunk.getWorld();
        int c_x = centerChunk.getX();
        int c_z = centerChunk.getZ();

        Chunk topChunk = world.getChunkAt(c_x+1, c_z);
        Chunk bottomChunk = world.getChunkAt(c_x-1, c_z);

        Chunk leftChunk = world.getChunkAt(c_x,c_z-1);
        Chunk rightChunk = world.getChunkAt(c_x,c_z+1);

        Chunk topRightChunk = world.getChunkAt(c_x+1, c_z+1);
        Chunk topLeftChunk = world.getChunkAt(c_x+1, c_z-1);

        Chunk bottomRightChunk = world.getChunkAt(c_x-1, c_z+1);
        Chunk bottomLeftChunk = world.getChunkAt(c_x-1, c_z-1);

        return new Chunk[] { centerChunk, topChunk, bottomChunk, leftChunk, rightChunk, topRightChunk, topLeftChunk, bottomRightChunk, bottomLeftChunk };

    }

    private void searchContainers() {

        for (Chunk c : nearbyChunks) {

            player.sendMessage("Looking in chunk " + c.toString());

            BlockState[] tileEntities = c.getTileEntities(true);

            player.sendMessage("Tile entities in chunk:" + tileEntities.length);

            for (BlockState blockState : tileEntities) {

                player.sendMessage(blockState.toString());

                if (blockState instanceof Chest || blockState instanceof ShulkerBox) {
                    player.sendMessage("Found one");
                    searchContainer(blockState);
                }

            }

        }

    }

    private void searchContainer(BlockState blockState) {

        Inventory inventory = null;

        if (blockState instanceof Chest) {
            inventory = ((Chest) blockState).getInventory();

        } else if (blockState instanceof ShulkerBox) {
            inventory = ((ShulkerBox) blockState).getInventory();

        }

        if (inventory!=null) {

            if (inventory.contains(material)) {
                results.put(blockState, blockState.getLocation());
            }

        }

    }

}
