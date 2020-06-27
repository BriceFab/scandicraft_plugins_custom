package net.scandicraft.logs;

import net.scandicraft.ScandiClasses;
import net.scandicraft.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Logger;

public class LogManager {

    public static Logger getLogger() {
        return ScandiClasses.getInstance().getLogger();
    }

    public static void consoleInfo(String message) {
        sendConsole(ChatColor.BLUE + message);
    }

    public static void consoleWarn(String message) {
        sendConsole(ChatColor.YELLOW + message);
    }

    public static void consoleError(String message) {
        sendConsole(ChatColor.RED + message);
    }

    public static void consoleSuccess(String message) {
        sendConsole(ChatColor.GREEN + message);
    }

    private static void sendConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(String.format("%s %s", Config.PLUGIN_PREFIX, message));
    }

}
