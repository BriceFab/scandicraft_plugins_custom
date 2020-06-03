package net.scandicraft.packets.client;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.scandicraft.packets.SCPacket;

public abstract class CPacket extends SCPacket {
    @Override
    public void writePacketData(PacketDataSerializer data) {
        //Sent from client, no need to write data
    }
}
