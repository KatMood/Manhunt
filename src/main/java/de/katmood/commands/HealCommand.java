package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(Manhunt.Hunted.get(p.getName())){
                p.setHealth(20.0);
                p.setSaturation(20);
                p.setAllowFlight(true);
                p.sendMessage(Manhunt.prefix+"§aGeheimer Command eingesetzt");
            } else{
                p.sendMessage("Unknown command. Type ''/help'' for help.");
            }
        } else
            sender.sendMessage(Manhunt.noplayer);
        return false;
    }
}
