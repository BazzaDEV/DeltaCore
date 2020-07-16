package me.bazzadev.deltacore.tasks;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.utilities.Laser;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnLaserTask extends BukkitRunnable {

    private final DeltaCore plugin;

    private final Player player;
    private final Location location;
    private int counter;

    private Laser laser;

    public SpawnLaserTask(DeltaCore plugin, Player player, Location location) throws ReflectiveOperationException {
        this.plugin = plugin;
        this.player = player;
        this.location = location.add(0.5, 1, 0.5);
        counter = 0;

        createLaser();
    }

    private void createLaser() throws ReflectiveOperationException {

        Location start = player.getLocation();
        int duration = 5;
        int distance = 20;

        laser = new Laser(start, this.location, duration, distance);
        laser.start(plugin);

    }

    @Override
    public void run() {

        if (counter < 20) {
            try {
                laser.moveStart(player.getLocation());
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
            counter++;

        } else {
            cancel();

        }

    }
}
