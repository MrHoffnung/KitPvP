package de.sqlcxde.kitpvp.utils;

import de.sqlcxde.kitpvp.KitPvP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.concurrent.*;
import java.util.logging.Level;

public class MySQL {

    private static ExecutorService executor;
    private static Connection con;
    private static FileConfiguration cfg;

    public static void connect() throws IOException {
        cfg = KitPvP.getInstance().getConfig();
        executor = Executors.newCachedThreadPool();
        String host = cfg.getString("mysql.host");
        int port = cfg.getInt("mysql.port");
        String database = cfg.getString("mysql.database");
        String username = cfg.getString("mysql.username");
        String password = cfg.getString("mysql.password");
        try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username, password);
                createTables();
                KitPvP.getInstance().getLogger().log(Level.FINEST, "The connection to the database has been established!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTables() {
        update("CREATE TABLE IF NOT EXISTS player_stats (player VARCHAR(100), uuid VARCHAR(100), kills INT(20), deaths INT(20), most INT(20))");
    }

    public static void disconnect() {
        if (MySQL.isConnected()) {
            try (Connection con = MySQL.getConnection();){
                con.close();
                KitPvP.getInstance().getLogger().log(Level.WARNING, "The connection to the database has been lost!");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        try {
            return !con.isClosed();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        return con;
    }

    private static ResultSet query(String query) throws IOException {
        ResultSet rs = null;
        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(query);
        }
        catch (SQLException e) {
            MySQL.connect();
            System.err.println(e);
        }
        return rs;
    }

    public static void update(String query) {
        try {
            PreparedStatement statement = con.prepareStatement(query);
            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getResult(final String statement) {
        if (MySQL.isConnected()) {
            Future<ResultSet> future = executor.submit(new Callable<ResultSet>(){

                @Override
                public ResultSet call() throws Exception {
                    return MySQL.query(statement);
                }
            });
            try {
                return future.get();
            }
            catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
