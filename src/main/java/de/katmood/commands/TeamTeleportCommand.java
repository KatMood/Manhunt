package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamTeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(Manhunt.noplayer);
        }
        Player p = (Player) sender;
        if(Manhunt.ttp){
            if(args.length == 0){
                if(Manhunt.getHuteds().contains(p.getName())){
                    if(Manhunt.getHuteds().size() == 2){
                        for(int i = 0; i < 2; i++){
                            if(!(Manhunt.getHuteds().get(i) == p.getName())){
                                Player target = (Player) Bukkit.getPlayer(Manhunt.getHuteds().get(i));
                                p.teleport(target);
                            }
                        }
                    }
                    if(!(Manhunt.getHuteds().size() == 2)){
                        p.sendMessage(Manhunt.prefix+"§cEs befinden sich zu viele Spieler in deinem team");
                        p.sendMessage(Manhunt.prefix+"§cum /teamteleport ohne Spielerangabe zu benutzen!");
                    }
                }
            }
        }

        return false;
    }
}
