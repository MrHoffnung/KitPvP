package de.sqlcxde.kitpvp.listeners;

import de.sqlcxde.kitpvp.utils.GamePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        GamePlayer gamePlayer = new GamePlayer(event.getPlayer());
        gamePlayer.getStatistics().create();
    }
}
