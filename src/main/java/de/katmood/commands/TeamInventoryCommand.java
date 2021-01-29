package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeamInventoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;

        if(Manhunt.tinv){
            if(Manhunt.getHunteds().contains(p.getName())){
                p.openInventory(Manhunt.huntedinv);
            }
            if(Manhunt.getOnlineHunters().contains(p.getName())){
                p.openInventory(Manhunt.hunterinv);
            }
        }

        return false;
    }
}
