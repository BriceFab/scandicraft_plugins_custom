package net.scandicraft.capacities.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class CapacitiesListener implements Listener {

    public CapacitiesListener() {
        SpigotScanner clientInput = new SpigotScanner(System.in);

    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        System.out.print("capacitiesListener onPlayerLogin");
    }

}
