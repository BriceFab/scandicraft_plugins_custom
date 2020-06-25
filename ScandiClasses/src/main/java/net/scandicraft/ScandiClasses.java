package net.scandicraft;

import net.scandicraft.capacities.listeners.CapacitiesListener;
import net.scandicraft.commands.ClasseCommands;
import net.scandicraft.commands.CommandList;
import net.scandicraft.commands.Commands;
import net.scandicraft.sql.SqlManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ScandiClasses extends JavaPlugin implements Listener {

    public static ScandiClasses INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new CapacitiesListener(), this);

        SqlManager.getInstance().init();

        //Register commands
        getCommand(CommandList.classeCommand).setExecutor(new ClasseCommands());
    }

    @Override
    public void onDisable() {
        SqlManager.getInstance().closeConnection();
    }

    public static ScandiClasses getInstance() {
        return INSTANCE;
    }

}
