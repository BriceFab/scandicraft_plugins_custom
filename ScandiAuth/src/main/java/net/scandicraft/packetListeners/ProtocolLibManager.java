package net.scandicraft.packetListeners;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketListener;
import net.scandicraft.ScandiAuth;
import org.bukkit.plugin.Plugin;

import java.util.Set;

public class ProtocolLibManager {

    private static final ProtocolLibManager INSTANCE = new ProtocolLibManager();

    private ProtocolLibManager() {
    }

    public void registerListeners(Plugin plugin) {
        this.registerPacketAdapter(new PingListener(plugin));
    }

    private void registerPacketAdapter(PacketAdapter packetAdapter) {
        try {
            Set<PacketListener> packetListeners = ProtocolLibrary.getProtocolManager().getPacketListeners();
//            ScandiAuth.getInstance().getLogger().info("PacketAdapters count: " + packetListeners.size());

//        ProtocolLibrary.getProtocolManager().removePacketListeners(packetAdapter.getPlugin());
//        ProtocolLibrary.getProtocolManager().removePacketListener(packetAdapter);

            if (!packetListeners.contains(packetAdapter)) {
                ProtocolLibrary.getProtocolManager().addPacketListener(packetAdapter);
                ScandiAuth.getInstance().getLogger().info("PacketAdapter " + packetAdapter.getClass() + " register successfully.");
            } else {
                ScandiAuth.getInstance().getLogger().warning("PacketAdapter " + packetAdapter.getClass() + " already register.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProtocolLibManager getInstance() {
        return INSTANCE;
    }
}
