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
                    p.sendMessage("ttp");
                }
            }
        }

        return false;
    }
}
