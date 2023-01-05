package de.sqlcxde.kitpvp.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class StreakHandler {

    private final GamePlayer gamePlayer;
    private HashMap<GamePlayer,Integer> streak;

    public StreakHandler(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
        this.streak = new HashMap<>();
    }

    public int getCurrentStreak() {
        return this.streak.get(this.gamePlayer);
    }

    public void resetStreak() {
        this.streak.put(this.gamePlayer, 0);
    }

    public void setCurrentStreak(int streak) {
        this.streak.put(this.gamePlayer, streak);
    }
}
