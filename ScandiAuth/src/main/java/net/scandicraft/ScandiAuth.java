package net.scandicraft;

import net.scandicraft.config.Config;
import net.scandicraft.logs.LogManager;
import net.scandicraft.packetListeners.PacketsListener;
import net.scandicraft.packetListeners.ProtocolLibManager;
import net.scandicraft.packets.CustomPacketManager;
import net.scandicraft.tasks.VerifyClientResponse;
import net.scandicraft.tasks.VerifyClientTask;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        final String hostname = e.getHostname();
        final long start_task_time = System.currentTimeMillis();

        try {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<VerifyClientResponse> verifyClientTask = executorService.submit(new VerifyClientTask(player, hostname));

            VerifyClientResponse verifyClientResponse = verifyClientTask.get();
            final boolean isUsingClient = verifyClientResponse.isUsingClient();
            final String message = verifyClientResponse.getMessage();

            if (isUsingClient) {
                e.allow();

                final long result_task_time = System.currentTimeMillis() - start_task_time;
                LogManager.consoleSuccess(String.format("Accept connection for %s in %d ms", player.getName(), result_task_time));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        playersUsingClient.add(player.getUniqueId());
                        player.sendMessage(ChatColor.GREEN + String.format(message, result_task_time));
                    }
                }.runTaskLater(this, 3);
            } else {
                LogManager.consoleError(String.format("Refuse connection for %s in %d ms for reason: %s", player.getName(), System.currentTimeMillis() - start_task_time, message));
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
            }

            executorService.shutdown();
        } catch (Exception exception) {
            exception.printStackTrace();
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.RED + "Impossible de se connecter au serveur d'authentification..");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (Config.ENABLE_DEBUG_PACKETS_LISTENER) {
            PacketsListener.injectPlayer(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        if (playersUsingClient.contains(uuid)) {
            playersUsingClient.remove(uuid);
        }

        if (Config.ENABLE_DEBUG_PACKETS_LISTENER) {
            PacketsListener.uninjectPlayer(e.getPlayer());
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
