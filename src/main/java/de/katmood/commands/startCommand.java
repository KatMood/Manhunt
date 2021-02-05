package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class startCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;

        Manhunt.started = true;

        for(Player cp : Bukkit.getOnlinePlayers()) {
            Manhunt.Alive.put(cp.getName(), true);
            Manhunt.savePlayerData();
        }

        for(Player cp : Bukkit.getOnlinePlayers()) {
            cp.setGameMode(GameMode.SURVIVAL);
            cp.getInventory().clear();
        }

        Bukkit.broadcastMessage(Manhunt.prefix+"§aDas Spiel wurde gestartet!");
        String message = Manhunt.prefix+"§7";
        String und = "§7 und §a";
        message += "Die Gejagten sind §a";
        for(String currentName : Manhunt.getHunteds())
            message += und+currentName;
        Bukkit.broadcastMessage(message.replaceFirst(und, ""));

        if(Manhunt.freeze) {
            for(int i = 0; i < Manhunt.getOnlineHunters().size(); i++) {
                Player target = Bukkit.getPlayer(Manhunt.getOnlineHunters().get(i));
                Manhunt.freezePlayer(target, Manhunt.freezeTime);
            }
        }
        if(Manhunt.timerenabled)
            Manhunt.timer.start();

        EffectGUI.applyEffectsToEveryPlayer();
        return false;
    }
}
