package net.scandicraft;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.scandicraft.config.ScandiCraftMultiplayer;
import net.scandicraft.packets.CustomPacketManager;
import net.scandicraft.packets.server.SPacketHelloWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScandiAuth extends JavaPlugin implements Listener {

    public static ScandiAuth INSTANCE;
    private List<UUID> playersUsingClient = new ArrayList<>();  //players qui utilisent le client ScandiCraft

    @Override
    public void onEnable() {
        INSTANCE = this;
        CustomPacketManager.registerPackets();
        Bukkit.getPluginManager().registerEvents(this, this);

        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, PacketType.Status.Server.SERVER_INFO) {
            @Override
            public void onPacketSending(PacketEvent event) {

                WrappedServerPing ping = event.getPacket().getServerPings().read(0);
                ping.setVersionProtocol(ScandiCraftMultiplayer.PING_VERSION);
                ping.setVersionName(String.format("[%s] %s", ScandiCraftMultiplayer.CLIENT_NAME, ScandiCraftMultiplayer.CLIENT_VERSION));

            }
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("ScandiAuth disable");
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {

        //TODO check username et uuid
        getLogger().info("login Player uuid  " + e.getPlayer().getUniqueId());
        getLogger().info("hostname " + e.getHostname());

        String[] hostname = e.getHostname().split(":")[0].split("\0");
        boolean isUsingClient = false;

        if (hostname.length == 2) {
            if (hostname[1].equals(ScandiCraftMultiplayer.AUTH_KEY)) {
                isUsingClient = true;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        playersUsingClient.add(e.getPlayer().getUniqueId());
                        e.getPlayer().sendMessage(ChatColor.GREEN + "Merci d'utiliser le client ScandiCraft !");
                        CustomPacketManager.sendCustomPacket(e.getPlayer(), new SPacketHelloWorld());
                    }
                }.runTaskLater(this, 2);
            }
        }

        if (!isUsingClient) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Veuillez télécharger le launcher ScandiCraft (https://scandicraft-mc.fr/jouer) !");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        getLogger().info("on join");
//        CustomPacketManager.sendCustomPacket(e.getPlayer(), new SPacketHelloWorld());

//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                CustomPacketManager.sendCustomPacket(e.getPlayer(), new SPacketHelloWorld());
//            }
//        }.runTaskLater(this, 2);

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (playersUsingClient.contains(uuid)) {
            playersUsingClient.remove(uuid);
        }
    }

    public static ScandiAuth getInstance() {
        return INSTANCE;
    }

    public List<UUID> getPlayersUsingClient() {
        return playersUsingClient;
    }

    public boolean isPlayerUsingClient(Player player) {
        return getPlayersUsingClient().contains(player.getUniqueId());
    }
}
