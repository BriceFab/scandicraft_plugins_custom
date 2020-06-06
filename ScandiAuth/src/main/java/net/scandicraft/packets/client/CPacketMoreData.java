package net.scandicraft.packets.client;

import net.minecraft.server.v1_8_R3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class CPacketMoreData extends CPacket {

    int rInt;
    double rDouble;
    float rFloat;
    byte[] rByteArray;
    boolean rBoolean;
    String rString;
    UUID rUUID;
    EntityPainting.EnumArt rEnum;
    BlockPosition rBlockPos;
    ItemStack rItemStack;
    NBTTagCompound rNBT;

    @Override
    public void readPacketData(PacketDataSerializer data) throws IOException {
        rInt = data.readInt();
        rDouble = data.readDouble();
        rFloat = data.readFloat();
        rByteArray = readByteArray(data);
        rBoolean = data.readBoolean();
        rString = readString(data);
        rUUID = readUUID(data);
        rEnum = readEnum(data, EntityPainting.EnumArt.class);
        rBlockPos = readBlockPosition(data);
        rItemStack = readItemStack(data);
        rNBT = readNBTTagCompound(data);
    }

    @Override
    public void handle(PacketListener listener) {
        System.out.print(rInt);
        System.out.print(rDouble);
        System.out.print(rFloat);
        System.out.print(Arrays.toString(rByteArray));
        System.out.print(rBoolean);
        System.out.print(rString);
        System.out.print(rUUID);
        System.out.print(rEnum);
        System.out.print(rBlockPos);
        System.out.print(rItemStack);
        System.out.print(rNBT);
    }

}
