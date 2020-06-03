package net.scandicraft.packets.server;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.scandicraft.packets.SCPacket;

public abstract class SPacket extends SCPacket {

    @Override
    public void handle(PacketListener listener) {
        //Sent from server, won't be getting message back
    }

    @Override
    public void readPacketData(PacketDataSerializer data) {
        //Sent from server, won't be reading data
    }
}
