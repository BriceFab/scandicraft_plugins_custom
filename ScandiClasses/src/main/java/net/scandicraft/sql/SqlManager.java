package net.scandicraft.sql;

import net.scandicraft.LogManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlManager {

    private static final SqlManager INSTANCE = new SqlManager();
    private String host, database, username, password;
    private int port;
    private Connection connection;

    private SqlManager() {
        loadConfig();
    }

    public void init() {
        host = SqlConfig.getConfig().getString(SqlConfig.host);
        port = SqlConfig.getConfig().getInt(SqlConfig.port);
        database = SqlConfig.getConfig().getString(SqlConfig.database);
        username = SqlConfig.getConfig().getString(SqlConfig.username);
        password = SqlConfig.getConfig().getString(SqlConfig.password);

        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection(
                        "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database,
                        this.username,
                        this.password
                ));

                LogManager.consoleSuccess("Mysql connected successfuly");
            }
        } catch (SQLException | ClassNotFoundException e) {
            LogManager.consoleError("Mysql connection error");

            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (getInstance() != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadConfig() {
        SqlConfig.getConfig().options().copyDefaults(true);
        SqlConfig.saveConfig();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public static SqlManager getInstance() {
        return INSTANCE;
    }
}
