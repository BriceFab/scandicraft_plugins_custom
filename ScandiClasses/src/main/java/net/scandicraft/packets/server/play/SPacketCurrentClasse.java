package net.scandicraft.packets.server.play;

import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.scandicraft.classes.IClasse;
import net.scandicraft.packets.server.SPacket;

import java.io.IOException;

/**
 * Informe au client sa classe actuelle
 */
public class SPacketCurrentClasse extends SPacket {
    private IClasse currentClasse = null;

    public SPacketCurrentClasse() {
    }

    public SPacketCurrentClasse(IClasse currentClasse) {
        this.currentClasse = currentClasse;
    }

    @Override
    public void writePacketData(PacketDataSerializer data) throws IOException {
        data.writeInt(this.currentClasse.getClassType().getId());
    }
}
