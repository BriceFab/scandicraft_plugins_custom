package net.scandicraft.packets.client;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;

import java.io.IOException;

public class CPacketAuthToken extends CPacket {

    public static String token;

    @Override
    public void readPacketData(PacketDataSerializer data) throws IOException {
        token = readString(data);
    }

    @Override
    public void handle(PacketListener listener) {
//        System.out.print("Receive token: " + token);
    }
}
