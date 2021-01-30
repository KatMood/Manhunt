package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModeratorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (command.getName().equalsIgnoreCase("moderator")) {
            if (!(sender instanceof Player))
                sender.sendMessage(Manhunt.noplayer);

            if(!Manhunt.Moderators.get(p))
                p.sendMessage(Manhunt.prefix+"§cDu bist leider kein Moderator und kannst darum keine Einstellungen ändern");
            if(Manhunt.Moderators.get(p))
                p.sendMessage(Manhunt.prefix+"§aDu bist ein Moderator und kannst darum sogar Einstellungen ändern.");
        }

        if (command.getName().equalsIgnoreCase("moderatorchange")) {
            if(!(sender instanceof Player) || sender == Bukkit.getPlayer("KatMood")); {
                Player target = Bukkit.getPlayer(args[0]);
                if(args.length < 2)
                    sender.sendMessage(Manhunt.prefix+"§c/moderatorchange <Spieler> <true/false>");
                if(args.length > 2)
                    sender.sendMessage(Manhunt.prefix+"§c/moderatorchange <Spieler> <true/false>");
                if(args.length == 2) {
                    if(target != null) {
                        if(args[1] == "true") {
                            Manhunt.Moderators.put(target.getName(), true);
                            Manhunt.saveModerators();
                        } else if (args[1] == "false") {
                            Manhunt.Moderators.put(target.getName(), false);
                            Manhunt.saveModerators();
                        } else
                            sender.sendMessage(Manhunt.prefix+"§c/moderatorchange <Spieler> <true/false>");
                    } else
                        sender.sendMessage(Manhunt.prefix+"§c/moderatorchange <Spieler> <true/false>");
                }
            }
        }

        return false;
    }
}
