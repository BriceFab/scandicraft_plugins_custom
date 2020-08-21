package net.scandicraft.packets.client;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListener;
import net.scandicraft.logs.LogManager;

import java.io.IOException;

public class CPacketAuthToken extends CPacket {

    private static String token;

    @Override
    public void readPacketData(PacketDataSerializer data) throws IOException {
        token = readString(data);
    }

    @Override
    public void handle(PacketListener listener) {
        LogManager.consoleWarn("receive token " + token);
    }

    public static String getToken() {
        final String previous_token = token;
        token = null;
        return previous_token;
    }
}
