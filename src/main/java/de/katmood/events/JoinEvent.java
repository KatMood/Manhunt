package de.katmood.events;

import de.katmood.manhunt.Manhunt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(!(Manhunt.getHuteds().contains(p))){
            Manhunt.Hunted.put(p.getName(), false);
        }
    }
}
