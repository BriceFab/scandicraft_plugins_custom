package net.scandicraft.packetListeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.scandicraft.config.ScandiCraftMultiplayer;
import org.bukkit.plugin.Plugin;

public class PingListener extends PacketAdapter {

    public PingListener(Plugin plugin) {
        super(plugin, PacketType.Status.Server.SERVER_INFO);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if (event.getPacketType() == PacketType.Status.Server.SERVER_INFO) {
            WrappedServerPing ping = event.getPacket().getServerPings().read(0);
            ping.setVersionProtocol(ScandiCraftMultiplayer.PING_VERSION);
            ping.setVersionName(String.format("[Launcher] %s", ScandiCraftMultiplayer.CLIENT_NAME));
        }
    }
}
