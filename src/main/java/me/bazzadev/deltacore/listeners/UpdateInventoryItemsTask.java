package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.staffmode.StaffGUIManager;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateInventoryItemsTask extends BukkitRunnable {

    private final StaffGUIManager staffGUIManager;

    public UpdateInventoryItemsTask(StaffGUIManager staffGUIManager) {
        this.staffGUIManager = staffGUIManager;
    }

    @Override
    public void run() {

        if (staffGUIManager.getMainGUI().getViewers().size() < 1) {
            staffGUIManager.setUpdate(false);
            // System.out.println("cancelled task");
            cancel();
        } else {
            staffGUIManager.refresh();
            // System.out.println("refreshed");
        }

    }
}
