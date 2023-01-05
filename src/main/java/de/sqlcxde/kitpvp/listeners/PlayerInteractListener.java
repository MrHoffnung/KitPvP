package de.sqlcxde.kitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getClickedBlock().getType() == Material.OAK_SIGN) {
            Sign sign = (Sign) event.getClickedBlock();
            if (sign.getLine(1).equalsIgnoreCase("§b§lNinja")) {
                event.getPlayer().sendMessage("NINJA");
            }
        }
     }
}
