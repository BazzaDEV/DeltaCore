package me.bazzadev.deltacore.listeners;

import me.bazzadev.deltacore.DeltaCore;
import me.bazzadev.deltacore.managers.StaffGUIManager;
import me.bazzadev.deltacore.tasks.UpdateInventoryItemsTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.scheduler.BukkitTask;

public class OpenInventoryListener implements Listener {

    private final StaffGUIManager staffGUIManager;
    private final DeltaCore plugin;

    public OpenInventoryListener(StaffGUIManager staffGUIManager, DeltaCore plugin) {
        this.staffGUIManager = staffGUIManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent event) {

        if (!event.getInventory().equals(staffGUIManager.getMainGUI())) return;

        if (event.getInventory().equals(staffGUIManager.getMainGUI())) {
            if (!staffGUIManager.getUpdate() && staffGUIManager.getMainGUI().getViewers().size() > 0) {
                staffGUIManager.setUpdate(true);
                // System.out.println("started task");
                BukkitTask task = new UpdateInventoryItemsTask(staffGUIManager).runTaskTimerAsynchronously(plugin, 0, 10);
            }
        }

    }

}
