package de.katmood.events;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage();
        if(e.getMessage().startsWith("#")){
            if(Manhunt.teamchat){
                if(Manhunt.Hunted.get(p.getName())){
                    for(Player hunted : Bukkit.getOnlinePlayers()){
                        if(Manhunt.Hunted.get(hunted.getName())){
                            e.setCancelled(true);
                            hunted.sendMessage("§7[TC] §a"+p.getName()+" §8>> §7"+msg.replaceAll("#", "§7"));
                        }
                    }
                } else if(!Manhunt.Hunted.get((p.getName()))){
                    for(Player hunter : Bukkit.getOnlinePlayers()){
                        if(!Manhunt.Hunted.get(hunter.getName())){
                            e.setCancelled(true);
                            hunter.sendMessage("§7[TC] §c"+p.getName()+" §8>> §7"+msg.replaceAll("#", "§7"));
                        }
                    }
                }
            }
        }
    }

}
