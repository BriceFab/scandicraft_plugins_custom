package net.scandicraft;

import net.scandicraft.config.ScandiCraftMultiplayer;
import net.scandicraft.http.HTTPClient;
import net.scandicraft.http.HTTPEndpoints;
import net.scandicraft.http.HTTPReply;
import net.scandicraft.http.HttpStatus;
import net.scandicraft.http.entity.VerifyTokenEntity;
import net.scandicraft.logs.LogManager;
import net.scandicraft.packetListeners.ProtocolLibManager;
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

public final class ScandiAuth extends JavaPlugin implements Listener {

    public static ScandiAuth INSTANCE;
    private final List<UUID> playersUsingClient = new ArrayList<>();  //players qui utilisent le client ScandiCraft

    @Override
    public void onEnable() {
        INSTANCE = this;
        CustomPacketManager.registerPackets();
        Bukkit.getPluginManager().registerEvents(this, this);

        ProtocolLibManager.getInstance().registerListeners(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ScandiAuth disable");
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        final Player player = e.getPlayer();

        LogManager.consoleInfo("Receive connection for " + player.getName() + " start checking");

        String[] hostname = e.getHostname().split(":")[0].split("\0");
        boolean isUsingClient = false;

        if (hostname.length == 2) {
            if (hostname[1].equals(ScandiCraftMultiplayer.AUTH_KEY)) {
                try {
//                    Thread.sleep(2000); //attends 2 secondes le token
                    final String token = CPacketAuthToken.token;
                    synchronized (token) {
                        token.wait(2000);
                    }

                    if (token == null || token.equals("") || token.length() <= 0) {
                        LogManager.consoleError("Refuse connection for " + player.getName() + " (no token)");
                        e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Erreur d'authentification: aucun token");
                        return;
                    }

                    VerifyTokenEntity entity = new VerifyTokenEntity(player.getName(), player.getUniqueId().toString(), token);
                    HTTPClient httpClient = new HTTPClient(token);
                    HTTPReply httpReply = httpClient.post(HTTPEndpoints.VERIFY_TOKEN, entity);
//                    getLogger().info("VerifyToken Response: " + httpReply.getStatusCode() + " - " + httpReply.getJsonResponse());

                    //Http traitement de la réponse
                    if (httpReply.getStatusCode() == HttpStatus.HTTP_OK) {
                        if (httpReply.getJsonResponse().get("username").getAsString().equals(player.getName()) && httpReply.getJsonResponse().get("uuid").getAsString().equals(player.getUniqueId().toString())) {
                            isUsingClient = true;
                        }
                    } else {
                        LogManager.consoleError("Refuse connection for " + player.getName() + " (http error)");
                        e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Erreur d'authentification: " + httpReply.getJsonResponse().get("error"));
                        return;
                    }
                } catch (Exception exception) {
                    LogManager.consoleError("Refuse connection for " + player.getName() + " (auth error)");
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Erreur lors de l'authentification");
                    exception.printStackTrace();
                    return;
                }
            }
        }

        if (!isUsingClient) {
            LogManager.consoleError("Refuse connection for " + player.getName() + " (not using client)");
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Veuillez télécharger le launcher ScandiCraft (https://scandicraft-mc.fr/jouer) !");
        } else {
            LogManager.consoleSuccess("Accept connection for " + player.getName());
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
