package de.sqlcxde.kitpvp.utils;

import de.sqlcxde.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GamePlayer {
    private final Player player;
    private final StatsHandler statsHandler;
    private final StreakHandler streakHandler;

    public GamePlayer(Player player) {
        this.player = player;
        this.streakHandler = new StreakHandler(this);
        this.statsHandler = new StatsHandler(this);
    }

    public boolean hasPermission(String permission) {
        return this.player.hasPermission(permission);
    }

    public void sendMessage(String message) {
        this.player.sendMessage(message);
    }

    public UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    public String getName() {
        return this.player.getName();
    }

    public double getRemainingHealth() {
        return this.player.getHealth();
    }

    public StatsHandler getStatistics() {
        return statsHandler;
    }

    public StreakHandler getStreakHandler() {
        return streakHandler;
    }

    public void kill(GamePlayer killer) {
        this.player.setHealth(0.0);
        this.getStatistics().addDeath();
        this.getStreakHandler().resetStreak();
        if (!KitPvP.getInstance().isGerman()) {
            Bukkit.broadcastMessage("§3" + this.getName() + " §7has been killed by §3" + killer.getName() + " §7with " + killer.getRemainingHealth() + " health left.");
        } else {
            Bukkit.broadcastMessage("§3" + killer.getName() + " §7hat §3" + this.getName() + " §7mit " + killer.getRemainingHealth() + " Leben getötet.");
        }
        if (KitPvP.getInstance().getConfig().getIntegerList("streakAnnouncements").contains(killer.getStreakHandler().getCurrentStreak())) {
            Bukkit.broadcastMessage("§1                 ");
            if (!KitPvP.getInstance().isGerman()) {
                Bukkit.broadcastMessage("§b" + killer.getName() + " §7is on a §b§l" + killer.getStreakHandler().getCurrentStreak() + " §r§7player killstreak!");
            } else {
                Bukkit.broadcastMessage("§b" + killer.getName() + " §7hat eine Killstreak von §b§l" + killer.getStreakHandler().getCurrentStreak() + " §r§7erreicht!");
            }
            Bukkit.broadcastMessage("§1                 ");
        }
        killer.getStatistics().getKills();
        killer.getStreakHandler().setCurrentStreak(killer.getStreakHandler().getCurrentStreak() + 1);

        Bukkit.getScheduler().runTaskLater(KitPvP.getInstance(), new Runnable() {
            @Override
            public void run() {
                player.spigot().respawn();

            }
        }, 1L);
    }
}
