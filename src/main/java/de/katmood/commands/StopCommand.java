package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

        Manhunt.huntedinv.clear();
        Manhunt.hunterinv.clear();
        Manhunt.saveTeamInventories();

        for(Player cp : Bukkit.getOnlinePlayers()) {
            cp.setGameMode(GameMode.SURVIVAL);
            cp.getInventory().clear();
        }

        Manhunt.timer.stop();

        Bukkit.broadcastMessage(Manhunt.prefix+"§cDas Spiel wurde gestoppt");

        for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
            String target = Bukkit.getOfflinePlayers()[i].getPlayer().getName();
            Manhunt.Frozen.put(target, 0);

        }


        return false;
    }
}
