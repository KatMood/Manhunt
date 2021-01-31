package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        int time = Integer.parseInt(args[1]);
        int second = time;
        int minute = (second / 60);
        int hours = (minute / 60);
        int minutes = (second / 60) % 60;
        int seconds = (second-((minutes-1)*60)) % 60;
        if(hours > 0) {
            target.sendMessage(Manhunt.prefix+"§aDu bist noch "+hours+" Stunden, "+minutes+" Minuten und "+seconds+" Sekunden eingefroren!");
        } else if (minutes > 0) {
            target.sendMessage(Manhunt.prefix+"§aDu bist noch "+minutes+" Minuten und "+seconds+" Sekunden eingefroren!");
        } else {
            target.sendMessage(Manhunt.prefix+"§aDu bist noch "+seconds+" Sekunden eingefroren!");
        }
        Manhunt.freezePlayer(p, time);

        return false;
    }
}
