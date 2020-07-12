package net.scandicraft.packets.client.play;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.logs.LogManager;
import net.scandicraft.packets.client.CPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.UUID;

public class CPacketChangeCapacity extends CPacket {
    private UUID playerId = null;
    private String nextCapacity = null;

    @Override
    public void readPacketData(PacketDataSerializer data) throws IOException {
        this.playerId = readUUID(data);                      //Player uuid
        this.nextCapacity = readString(data);                //next capacity id
    }

    @Override
    public void handle(PacketListener listener) {
        Player player = Bukkit.getPlayer(this.playerId);
        if (player != null) {
            if (this.nextCapacity.equals("next_from_list")) {
                CapacityManager.getInstance().selectNextCapacity(player);
            } else {
                LogManager.consoleWarn(this.getClass().getSimpleName() + " selection specific capacity " + this.nextCapacity);
//                CapacityManager.getInstance().changeCurrentCapacity(player, null);
            }
        } else {
            LogManager.consoleWarn(this.getClass().getSimpleName() + " - receive fake player uuid");
        }

    }
}
