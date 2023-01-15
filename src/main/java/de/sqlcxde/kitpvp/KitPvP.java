package de.sqlcxde.kitpvp;

import de.sqlcxde.kitpvp.listeners.PlayerInteractListener;
import de.sqlcxde.kitpvp.listeners.PlayerLoginListener;
import de.sqlcxde.kitpvp.listeners.SignChangeListener;
import de.sqlcxde.kitpvp.utils.LocationsHandler;
import de.sqlcxde.kitpvp.utils.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;

public final class KitPvP extends JavaPlugin {
    private static KitPvP instance;
    private boolean german;
    private String serverName;
    private LocationsHandler locations;

    @Override
    public void onEnable() {
        instance = this;
        this.registerCommands();
        this.registerEvents();
        this.loadFiles();
        this.loadDatabase();
        getLogger().info("The plugin was enabled successfully!");
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
        getLogger().info("The plugin was disabled successfully!");
    }

    private void registerCommands() {

    }

    private void registerEvents() {
    }

    private void loadFiles() {
        this.locations = new LocationsHandler();
        try {
            if (!getDataFolder().mkdir()) getDataFolder().createNewFile();
            getConfig().options().copyDefaults(true);
            saveConfig();
            this.german = getConfig().getBoolean("german");
            this.serverName = getConfig().getString("serverName");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDatabase() {
            try {
                MySQL.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static KitPvP getInstance() {
        return instance;
    }

    public LocationsHandler getLocations() {
        return locations;
    }

    public boolean isGerman() {
        return german;
    }

    public String getServerName() {
        return serverName;
    }
}
