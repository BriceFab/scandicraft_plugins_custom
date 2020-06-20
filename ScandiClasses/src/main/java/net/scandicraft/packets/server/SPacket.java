package net.scandicraft.packets.server;

import net.minecraft.server.v1_8_R3.*;
import net.scandicraft.packets.SCPacket;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import java.io.IOException;
import java.util.UUID;

public abstract class SPacket extends SCPacket {

    @Override
    public void handle(PacketListener listener) {
        //Sent from server, won't be getting message back
    }

    @Override
    public void readPacketData(PacketDataSerializer data) throws IOException {
        //Sent from server, won't be reading data
    }

    public void writeString(PacketDataSerializer data, String msg) {
        data.writeInt(msg.length());
        data.a(msg);
    }

    public void writeUUID(PacketDataSerializer data, UUID uuid) {
        data.a(uuid);
    }

    public void writeNBTTagCompound(PacketDataSerializer data, NBTTagCompound nbt) {
        data.a(nbt);
    }

    public void writeItemStack(PacketDataSerializer data, ItemStack is) {
        data.a(is);
    }

    public void writeItemStackBukkit(PacketDataSerializer data, org.bukkit.inventory.ItemStack is) {
        writeItemStack(data, CraftItemStack.asNMSCopy(is));
    }

    public void writeBlockPosition(PacketDataSerializer data, BlockPosition pos) {
        data.a(pos);
    }

    public void writeBlockPosition(PacketDataSerializer data, Location loc) {
        this.writeBlockPosition(data, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
    }

    public void writeEnum(PacketDataSerializer data, final Enum<?> in) {
        data.a(in);
    }

    public void writeByteArray(PacketDataSerializer data, byte... bytes) {
        data.a(bytes);
    }
}
