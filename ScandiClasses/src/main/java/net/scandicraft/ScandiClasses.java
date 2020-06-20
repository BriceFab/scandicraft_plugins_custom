package net.scandicraft;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import net.scandicraft.config.ScandiCraftMultiplayer;
import net.scandicraft.http.HTTPClient;
import net.scandicraft.http.HTTPEndpoints;
import net.scandicraft.http.HTTPReply;
import net.scandicraft.http.HttpStatus;
import net.scandicraft.http.entity.VerifyTokenEntity;
import net.scandicraft.packets.CustomPacketManager;
import net.scandicraft.packets.client.CPacketAuthToken;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class ScandiClasses extends JavaPlugin implements Listener {

    public static ScandiClasses INSTANCE;
    private final List<UUID> playersUsingClient = new ArrayList<>();  //players qui utilisent le client ScandiCraft

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
                ping.setVersionName(String.format("[Launcher] %s", ScandiCraftMultiplayer.CLIENT_NAME));

            }
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("ScandiClasses disable");
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        final Player player = e.getPlayer();

        String[] hostname = e.getHostname().split(":")[0].split("\0");
        boolean isUsingClient = false;

        if (hostname.length == 2) {
            if (hostname[1].equals(ScandiCraftMultiplayer.AUTH_KEY)) {
                try {
                    VerifyTokenEntity entity = new VerifyTokenEntity(player.getName(), player.getUniqueId().toString(), CPacketAuthToken.token);
                    HTTPClient httpClient = new HTTPClient(CPacketAuthToken.token);
                    HTTPReply httpReply = httpClient.post(HTTPEndpoints.VERIFY_TOKEN, entity);
//                    getLogger().info("VerifyToken Response: " + httpReply.getStatusCode() + " - " + httpReply.getJsonResponse());

                    //Http traitement de la réponse
                    if (httpReply.getStatusCode() == HttpStatus.HTTP_OK) {
                        if (httpReply.getJsonResponse().get("username").getAsString().equals(player.getName()) && httpReply.getJsonResponse().get("uuid").getAsString().equals(player.getUniqueId().toString())) {
                            isUsingClient = true;
                        }
                    } else {
                        e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Erreur d'authentification: " + httpReply.getJsonResponse().get("error"));
                        return;
                    }
                } catch (Exception exception) {
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Erreur lors de l'authentification");
                    return;
                }
            }
        }

        if (!isUsingClient) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Veuillez télécharger le launcher ScandiCraft (https://scandicraft-mc.fr/jouer) !");
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    playersUsingClient.add(player.getUniqueId());
                    player.sendMessage(ChatColor.GREEN + "Authentifié avec succès sur ScandiCraft !");
                }
            }.runTaskLater(this, 3);
        }
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
