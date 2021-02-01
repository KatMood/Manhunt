package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;

        Manhunt.started = false;

        Bukkit.broadcastMessage(Manhunt.prefix+"§cDas Spiel wurde gestoppt");

        for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
            Player target = Bukkit.getOfflinePlayers()[i].getPlayer();
            Manhunt.Frozen.put(target.getName(), 0);
        }

        Manhunt.timer.stop();

        return false;
    }
}
