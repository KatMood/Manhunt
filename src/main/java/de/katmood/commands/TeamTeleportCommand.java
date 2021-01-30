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
                if(Manhunt.getHunteds().contains(p.getName())){
                    if(Manhunt.getHunteds().size() == 2){
                        for(int i = 0; i < 2; i++){
                            if(!(Manhunt.getHunteds().get(i) == p.getName())){
                                Player target = (Player) Bukkit.getPlayer(Manhunt.getHunteds().get(i));
                                p.teleport(target);
                            }
                        }
                    }
                    if(Manhunt.getHunteds().size() > 2){
                        p.sendMessage(Manhunt.prefix+"§cEs befinden sich zu viele Spieler in deinem team");
                        p.sendMessage(Manhunt.prefix+"§cum /teamteleport ohne Spielerangabe zu benutzen!");
                    }
                    if(Manhunt.getHunteds().size() < 2) {
                        p.sendMessage(Manhunt.prefix+"§cDu bist der einzige Spieler in deinem Team!");
                    }
                }
            }
            if(args.length == 1) {
                if(Manhunt.getHunteds().contains(p.getName())){
                    if(Manhunt.getHunteds().contains(args[0])){
                        Player target = Bukkit.getPlayer(args[0]);
                        p.teleport(target);
                    } else
                        p.sendMessage(Manhunt.prefix+"§cDieser Spieler befindet sich nicht in deinem Team!");
                }
            }

            if(args.length > 1) {
                p.sendMessage(Manhunt.prefix+"§c/teamteleport <Spieler>");
            }
            if(args.length == 0){
                if(Manhunt.getOnlineHunters().contains(p.getName())){
                    if(Manhunt.getOnlineHunters().size() == 2){
                        for(int i = 0; i < 2; i++){
                            if(!(Manhunt.getOnlineHunters().get(i) == p.getName())){
                                Player target = (Player) Bukkit.getPlayer(Manhunt.getOnlineHunters().get(i));
                                p.teleport(target);
                            }
                        }
                    }
                    if(Manhunt.getOnlineHunters().size() > 2){
                        p.sendMessage(Manhunt.prefix+"§cEs befinden sich zu viele Spieler in deinem team");
                        p.sendMessage(Manhunt.prefix+"§cum /teamteleport ohne Spielerangabe zu benutzen!");
                    }
                    if(Manhunt.getOnlineHunters().size() < 2) {
                        p.sendMessage(Manhunt.prefix+"§cDu bist der einzige Spieler in deinem Team!");
                    }
                }
            }
            if(args.length == 1) {
                if(Manhunt.getOnlineHunters().contains(p.getName())){
                    if(Manhunt.getOnlineHunters().contains(args[0])){
                        Player target = Bukkit.getPlayer(args[0]);
                        p.teleport(target);
                    } else
                        p.sendMessage(Manhunt.prefix+"§cDieser Spieler befindet sich nicht in deinem Team!");
                }
            }
            if(args.length > 1) {
                p.sendMessage(Manhunt.prefix+"§c/teamteleport <Spieler>");
            }
        } else
            p.sendMessage(Manhunt.prefix+"§cTeamTeleport ist deaktiviert!");

        return false;
    }
}
