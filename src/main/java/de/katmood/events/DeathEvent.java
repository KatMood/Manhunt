package de.katmood.events;

import de.katmood.manhunt.Manhunt;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if(Manhunt.started) {
            if(Manhunt.kill_all) {
                if(Manhunt.getHunteds().contains(p.getName())) {
                    Manhunt.Alive.put(p.getName(), false);
                    Manhunt.savePlayerData();
                    p.kickPlayer("§cDu bist gestorben!");
                }
            }
        }
    }

}
