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
        if(!(Manhunt.getHunteds().contains(p.getName())) && !(Manhunt.getHunters().contains(p.getName()))){
            Manhunt.Hunted.put(p.getName(), false);
        }
        if(!(Manhunt.Frozen.containsKey(p.getName()))) {
            Manhunt.Frozen.put(p.getName(), 0);
        }
    }
}
