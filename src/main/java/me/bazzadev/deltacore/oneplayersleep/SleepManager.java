package me.bazzadev.deltacore.oneplayersleep;

import me.bazzadev.deltacore.utilities.ChatUtil;
import me.bazzadev.deltacore.utilities.Vars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class SleepManager {

    private final BossBarCountdown bossBarCountdown;

    public SleepManager(BossBarCountdown bossBarCountdown) {
        this.bossBarCountdown = bossBarCountdown;
    }

    private boolean isStarted = false;
    private boolean cancel = false;

    private ArrayList<UUID> sleepingList = new ArrayList<>();
    private int sleepCount;

    public void addPlayer(Player player) {
        sleepingList.add(player.getUniqueId());
        sleepCount += 1;

        Bukkit.broadcastMessage(ChatUtil.color(Vars.SLEEP_PREFIX + ChatUtil.playerNameWithPrefix(player) + " &falso wants to sleep through the night!"));

    }

    public void removePlayer(Player player) {
        sleepingList.remove(player.getUniqueId());
        sleepCount-= 1;

        Bukkit.broadcastMessage(ChatUtil.color(Vars.SLEEP_PREFIX + ChatUtil.playerNameWithPrefix(player) + " &7changed their mind."));
    }

    public int getCount() {
        return sleepCount;
    }

    public void start() {
        isStarted = true;
        bossBarCountdown.createBar();
        bossBarCountdown.startCountdown();
    }

    public void cancel() {
        cancel = true;
        isStarted = false;
    }

    public void sleep() {

    }

    public boolean isStarted() {
        return isStarted;
    }

//    private HashMap<UUID, String> sleepListYes = new HashMap<>();
//    private HashMap<UUID, String> sleepListNo = new HashMap<>();
//
//    private int countYes = sleepListYes.size();
//    private int countNo = sleepListNo.size();

//    public void voteYes(Player player) {
//
//        UUID uuid = player.getUniqueId();
//        String playerName = ChatUtil.playerNameWithPrefix(player);
//
//        sleepListNo.remove(uuid);
//        sleepListYes.put(uuid, playerName);
//
//        Bukkit.broadcastMessage("");
//
//    }
//
//    public void voteNo(Player player) {
//
//        UUID uuid = player.getUniqueId();
//        String playerName = ChatUtil.playerNameWithPrefix(player);
//
//        sleepListYes.remove(uuid);
//        sleepListNo.put(uuid, playerName);
//
//    }



}
