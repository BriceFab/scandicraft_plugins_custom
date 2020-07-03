package net.scandicraft;

import net.scandicraft.capacities.CapacityManager;
import net.scandicraft.capacities.listeners.CapacitiesListener;
import net.scandicraft.classes.ClasseManager;
import net.scandicraft.classes.IClasse;
import net.scandicraft.commands.ClasseCommands;
import net.scandicraft.commands.CommandList;
import net.scandicraft.sql.SqlManager;
import net.scandicraft.sql.manager.impl.SqlClassesManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScandiClasses extends JavaPlugin implements Listener {

    public static ScandiClasses INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new CapacitiesListener(), this);

        //Initialise l'instance sql
        SqlManager.getInstance().init();

        //Register commands
        getCommand(CommandList.classeCommand).setExecutor(new ClasseCommands());

        //Enregistre tous les joueurs
        Bukkit.getOnlinePlayers().forEach(player -> {
            IClasse playerClasse = SqlClassesManager.getInstance().getPlayerClasse(player);
            if (playerClasse != null) {
                ClasseManager.getInstance().registerPlayer(player, playerClasse);
            }
        });
    }

    @Override
    public void onDisable() {
        //Ferme la connexion MySql
        SqlManager.getInstance().closeConnection();

        //Desenregistre tous les joueurs
        Bukkit.getOnlinePlayers().forEach(player -> ClasseManager.getInstance().unregisterPlayer(player));
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        final Player player = e.getPlayer();

        //Si il a une classe, on l'enregistre dans le manager
        IClasse playerClasse = SqlClassesManager.getInstance().getPlayerClasse(player);
        if (playerClasse != null) {
            ClasseManager.getInstance().registerPlayer(player, playerClasse);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        final Player player = e.getPlayer();

        //Desenregistre le joueur
        ClasseManager.getInstance().unregisterPlayer(player);

        //Desenregistre la capacité sélectionnée
        CapacityManager.getInstance().removeCurrentCapacity(player);

        //Enlève tout les cooldowns qui ont expirés
        CapacityManager.getInstance().playerRemoveAllExpiredCooldowns(player);
    }

    public static ScandiClasses getInstance() {
        return INSTANCE;
    }

}
