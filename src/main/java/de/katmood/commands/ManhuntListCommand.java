package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManhuntListCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            Manhunt.loadPlayerData();
            p.sendMessage(Manhunt.prefix+"§7Die gejagten Spieler sind §c"+Manhunt.getHunteds().get(0)+" §7und §c"+ Manhunt.getHunteds().get(1));
            Manhunt.savePlayerData();
            //p.sendMessage(Manhunt.huntedArray.toString());
        } else
            sender.sendMessage(Manhunt.noplayer);
        return false;
    }
}
