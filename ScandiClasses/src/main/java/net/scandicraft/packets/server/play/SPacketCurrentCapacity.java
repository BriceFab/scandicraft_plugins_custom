package net.scandicraft.packets.server.play;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.scandicraft.capacities.ICapacity;
import net.scandicraft.packets.server.SPacket;

import java.io.IOException;

public class SPacketCurrentCapacity extends SPacket {
    private ICapacity currentCapactiy = null;

    public SPacketCurrentCapacity() {
    }

    public SPacketCurrentCapacity(ICapacity currentCapacity) {
        this.currentCapactiy = currentCapacity;
    }

    @Override
    public void writePacketData(PacketDataSerializer data) throws IOException {
        writeString(data, this.currentCapactiy.getUniqueIdentifier());
    }
}
