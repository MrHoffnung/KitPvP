package de.sqlcxde.kitpvp.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsHandler {
    private final GamePlayer gamePlayer;

    public StatsHandler(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public void create() {
        if (!isPlayerExisting()) {
            MySQL.update("INSERT INTO player_stats (player,uuid,kills,deaths,most) VALUES ('" + this.gamePlayer.getName() + "','" + this.gamePlayer.getUniqueId() + "',0,0,0);");
        }
    }

    private boolean isPlayerExisting() {
        ResultSet rs = MySQL.getResult("SELECT * FROM player_stats WHERE uuid = '" + this.gamePlayer.getUniqueId() + "'");
        try {
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Integer getKills() {
        ResultSet rs = MySQL.getResult("SELECT kills FROM player_stats WHERE uuid = '" + this.gamePlayer.getUniqueId() + "'");
        try {
            while (rs.next()) {
                return rs.getInt("kills");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer getDeaths() {
        ResultSet rs = MySQL.getResult("SELECT deaths FROM player_stats WHERE uuid = '" + this.gamePlayer.getUniqueId() + "'");
        try {
            while (rs.next()) {
                return rs.getInt("deaths");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer getMostKills() {
        ResultSet rs = MySQL.getResult("SELECT most FROM player_stats WHERE uuid = '" + this.gamePlayer.getUniqueId() + "'");
        try {
            while (rs.next()) {
                return rs.getInt("most");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getKd() {
        double kd = (double) getKills() / getDeaths();
        return kd;
    }

    public void addKill() {
        int kills = getKills();
        kills++;
        MySQL.update("UPDATE player_stats SET kills = '" + kills + "' WHERE uuid = '" + this.gamePlayer.getUniqueId() + "'");
    }

    public void addDeath() {
        int deaths = getDeaths();
        deaths++;
        MySQL.update("UPDATE player_stats SET deaths = '" + deaths + "' WHERE uuid = '" + this.gamePlayer.getUniqueId() + "'");
    }

    public void updateMostKills(int kills) {
        if (kills > getMostKills()) {
            MySQL.update("UPDATE player_stats SET most = '" + kills + "' WHERE uuid = '" + this.gamePlayer.getUniqueId() + "'");
        }
    }
}
