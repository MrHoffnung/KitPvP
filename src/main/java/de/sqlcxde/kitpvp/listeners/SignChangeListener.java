package de.sqlcxde.kitpvp.listeners;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Objects;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (Objects.requireNonNull(event.getLine(0)).contains("[Ninja]")) {
            event.setLine(0, null);
            event.setLine(1, "§b§lNinja");
            event.setLine(2, null);
            event.setLine(3, null);
        }
    }
}
