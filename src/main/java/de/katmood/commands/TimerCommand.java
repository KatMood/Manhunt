package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            if(args.length == 1 && args[0].equalsIgnoreCase("start")){
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Manhunt.timer.start();
                    return true;
                }
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("stop")){
                Manhunt.timer.stop();
                return true;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("pause")){
                Manhunt.timer.pause();
                return true;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("resume")) {
                Manhunt.timer.resume();
                return true;
            }

            sender.sendMessage("§c/timer <start ¦ stop ¦ pause ¦ resume>");
            sender.sendMessage("§4ACHTUNG! §cBenutze /timer <start ¦ stop> nur im Notfall,");
            sender.sendMessage("§cDer Timer startet und stoppt automatisch!");

        } else
            sender.sendMessage(Manhunt.noplayer);

        return false;
    }
}
