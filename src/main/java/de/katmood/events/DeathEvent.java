package de.katmood.events;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathEvent implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.setDeathMessage("§7§l[§c§l!§7§l] §7"+p.getName()+" ist gestorben!");
        if(Manhunt.started) {
            if(Manhunt.kill_all) {
                if(Manhunt.getHunteds().contains(p.getName())) {
                    Manhunt.Alive.put(p.getName(), false);
                    Manhunt.savePlayerData();

                    Manhunt.AliveHunteds--;
                    if(Manhunt.AliveHunteds == 0) {
                        Manhunt.stopCommand();
                    }
                    if(Manhunt.AliveHunteds > 0) {
                        p.kickPlayer("§cDu bist gestorben!");
                    }
                }
            }
        }
    }

}
