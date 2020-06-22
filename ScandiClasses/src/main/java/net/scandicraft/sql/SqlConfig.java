package net.scandicraft.sql;

import net.scandicraft.ScandiClasses;
import org.bukkit.configuration.file.FileConfiguration;

public class SqlConfig {

    //Database
    public static String host = "host";
    public static String port = "port";
    public static String database = "database";
    public static String username = "username";
    public static String password = "password";

    //Tables
    public static String tables = "tables";
    public static String classeTable = "classe";

    //Functions
    public static FileConfiguration getConfig() {
        return ScandiClasses.getInstance().getConfig();
    }

    public static void saveConfig() {
        ScandiClasses.getInstance().saveConfig();
    }

    public static String getTable(String name) {
        return tables + "." + name;
    }

}
