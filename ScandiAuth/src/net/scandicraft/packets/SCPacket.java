package net.scandicraft.packets;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;

import java.io.IOException;

public abstract class SCPacket implements Packet {
    @Override
    public void a(PacketDataSerializer packetDataSerializer) throws IOException {
        readPacketData(packetDataSerializer);
    }

    @Override
    public void b(PacketDataSerializer packetDataSerializer) throws IOException {
        writePacketData(packetDataSerializer);
    }

    @Override
    public void a(PacketListener packetListener) {
        handle(packetListener);
    }

    public abstract void readPacketData(PacketDataSerializer data);

    public abstract void writePacketData(PacketDataSerializer data);

    public abstract void handle(PacketListener listener);
}
