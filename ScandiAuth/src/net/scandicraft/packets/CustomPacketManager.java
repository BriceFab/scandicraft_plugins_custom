package net.scandicraft.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Protocol;
import com.comphenix.protocol.PacketType.Sender;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.reflect.FieldUtils;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.google.common.collect.BiMap;
import net.minecraft.server.v1_8_R3.EnumProtocol;
import net.minecraft.server.v1_8_R3.EnumProtocolDirection;
import net.minecraft.server.v1_8_R3.Packet;
import net.scandicraft.ScandiAuth;
import net.scandicraft.packets.client.CPacketMoreData;
import net.scandicraft.packets.server.SPacket;
import net.scandicraft.packets.server.SPacketHelloWorld;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CustomPacketManager {

    public static final HashMap<Class<? extends SCPacket>, PacketType> packetToType = new HashMap<>();

    public static void registerPackets() {
        registerPacket(SPacketHelloWorld.class, PacketsID.SPacketHelloWorld, Sender.SERVER);
        registerPacket(CPacketMoreData.class, PacketsID.CPacketMoreData, Sender.CLIENT);
    }

    public static void registerPacket(Class<? extends SCPacket> packetClass, int packetId, Sender sender) {
        final PacketType packetType = new PacketType(Protocol.PLAY, sender, packetId, packetId); //TODO Remplace packetID with -1 if bugs
        packetToType.put(packetClass, packetType);

        final EnumProtocol protocol = EnumProtocol.PLAY;

        final EnumProtocolDirection direction = packetType.isClient() ? EnumProtocolDirection.SERVERBOUND : EnumProtocolDirection.CLIENTBOUND;

        try {
            //protocol.a().put(packetIdm packetClass);

            Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>> theMap = (Map<EnumProtocolDirection, BiMap<Integer, Class<? extends Packet<?>>>>) FieldUtils.readField(protocol, "j", true);
            BiMap<Integer, Class<? extends Packet<?>>> biMap = theMap.get(direction);
            biMap.put(packetId, (Class<? extends Packet<?>>) packetClass);
            theMap.put(direction, biMap);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Map<Class<?>, EnumProtocol> map = (Map<Class<?>, EnumProtocol>) Accessors.getFieldAccessor(EnumProtocol.class, Map.class, true).get("protocol");
        map.put(packetClass, protocol);

    }

    public static void sendCustomPacket(Player player, SPacket packet) {

        //Check if player using ScandiCraft Client before sending packet
        if (!ScandiAuth.getInstance().isPlayerUsingClient(player)) {
            ScandiAuth.getInstance().getLogger().warning("Player " + player.getName() + " is not using ScandiCraft Client. Packet " + packet.getClass().getSimpleName() + " is not sent !");
            return;
        }

        PacketContainer container = new PacketContainer(packetToType.get(packet.getClass()), packet);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public static PacketType getCustomPacketType(Class<? extends SCPacket> packetClass) {
        return packetToType.get(packetClass);
    }

}
