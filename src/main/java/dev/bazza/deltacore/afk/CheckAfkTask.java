package dev.bazza.deltacore.afk;

import dev.bazza.deltacore.system.Server;
import dev.bazza.deltacore.data.config.Config;
import dev.bazza.deltacore.data.config.ConfigPath;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CheckAfkTask extends BukkitRunnable {

    private final Server server;
    private final Config config;
    private final AFKManager afkManager;

    private final long timeout;

    public CheckAfkTask(Server server, Config config, AFKManager afkManager) {
        this.server = server;
        this.config = config;
        this.afkManager = afkManager;

        this.timeout = Long.valueOf((Integer) config.get(ConfigPath.AUTO_AFK_TIMEOUT));
    }

    @Override
    public void run() {

        server.getOnlineUsers().forEach(((uuid, player) -> {
            if (!player.isAfk()) {
                long currentTime = new Date().getTime();
                long timeElapsed = currentTime - player.getLastActiveTime();

                if (timeElapsed > TimeUnit.SECONDS.toMillis(timeout)) {
                    afkManager.toggleAFK(player, true, true);
                }


            }
        }));

    }
}
