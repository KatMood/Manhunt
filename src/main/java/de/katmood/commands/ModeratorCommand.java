package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sun.tools.jar.Main;

public class ModeratorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("moderator")) {
            Player p = (Player) sender;
            if (!(sender instanceof Player))
                sender.sendMessage(Manhunt.noplayer);

            if(!Manhunt.Moderators.get(p.getName()))
                p.sendMessage(Manhunt.prefix+"§cDu bist leider kein Moderator und kannst darum keine Einstellungen ändern");
            if(Manhunt.Moderators.get(p.getName()))
                p.sendMessage(Manhunt.prefix+"§aDu bist ein Moderator und kannst darum sogar Einstellungen ändern.");
        }

        if (command.getName().equalsIgnoreCase("moderatorchange")) {
            if(!(sender instanceof Player) || sender.getName().equalsIgnoreCase("KatMood")) {
                if(args.length < 2)
                    sender.sendMessage(Manhunt.prefix+"§c/moderatorchange <Spieler> <true/false>");
                if(args.length > 2)
                    sender.sendMessage(Manhunt.prefix+"§c/moderatorchange <Spieler> <true/false>");
                if(args.length == 2) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null) {
                        if(args[1].equalsIgnoreCase("true")) {
                            Manhunt.Moderators.put(target.getName(), true);
                            sender.sendMessage(Manhunt.prefix+"§7"+target.getName()+" §aist nun ein Moderator.");
                            Manhunt.saveModerators();
                        } else if (args[1].equalsIgnoreCase("false")) {
                            Manhunt.Moderators.put(target.getName(), false);
                            sender.sendMessage(Manhunt.prefix+"§7"+target.getName()+" §aist nun kein Moderator.");
                            Manhunt.saveModerators();
                        } else
                            sender.sendMessage(Manhunt.prefix+"§c/moderatorchange <Spieler> <true/false>");
                    } else
                        sender.sendMessage(Manhunt.prefix+"§c/moderatorchange <Spieler> <true/false>");
                }
            } else
                sender.sendMessage(Manhunt.prefix+"§cDu darfst das nicht tun!");
        }

        return false;
    }
}
