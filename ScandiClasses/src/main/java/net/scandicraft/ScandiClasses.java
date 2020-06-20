package net.scandicraft;

import net.scandicraft.capacities.listeners.CapacitiesListener;
import net.scandicraft.packets.CustomPacketManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScandiClasses extends JavaPlugin implements Listener {

    public static ScandiClasses INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        CustomPacketManager.registerPackets();
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new CapacitiesListener(), this);
    }

    public static ScandiClasses getInstance() {
        return INSTANCE;
    }

}
