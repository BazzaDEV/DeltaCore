package me.bazzadev.deltacore.tasks;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnParticleTask extends BukkitRunnable {

    private final Player player;
    private final Particle particle;
    private final Location location;

    private int counter;

    public SpawnParticleTask(Player player, Particle particle, Location location) {
        this.player = player;
        this.particle = particle;
        this.location = location;

        counter = 0;
    }

    @Override
    public void run() {

        if (counter < 20) {

            player.spawnParticle(particle, location.add(0.5, 1, 0.5), 1);
            counter++;

        } else {
            cancel();

        }

    }
}
