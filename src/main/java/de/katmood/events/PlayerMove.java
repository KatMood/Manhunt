package de.katmood.events;

import de.katmood.manhunt.Manhunt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if(Manhunt.Frozen.get(p.getName()) > 0) {
            e.setCancelled(true);
        }
    }

}
