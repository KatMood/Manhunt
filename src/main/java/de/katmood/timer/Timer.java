package de.katmood.timer;

import de.katmood.manhunt.Manhunt;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {

    public static int SchedulerID;

    String message = "§6§lJust a simple test message!";

    public void start(Player player) {
        SchedulerID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Manhunt.plugin, new Runnable() {
            @Override
            public void run() {
                if (!Manhunt.timerpaused) {
                    Manhunt.time += 100;
                }
                int hours = (Manhunt.time / 3600000);
                int minutes = (Manhunt.time / 60000) % 60;
                int seconds = (Manhunt.time / 1000) % 60;
                int milliseconds = (Manhunt.time / 10) % 100;

                StringBuilder message = new StringBuilder("§7§lTimer:§a§l ");
                message.append(String.format("%02d", hours)).append(":");
                message.append(String.format("%02d", minutes)).append(":");
                message.append(String.format("%02d", seconds)).append(":");
                message.append(String.format("%02d", milliseconds));

                if(Manhunt.timerpaused) {
                    message.append(" §c(pausiert)");
                }
                for(Player player : Bukkit.getOnlinePlayers()) {
                    sendActionbar(player, message.toString());
                }
            }
        }, 0, 2);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(SchedulerID);
        Manhunt.time = 0;
    }

    public void pause() {
        Manhunt.timerpaused = true;
    }

    public void resume() {
        Manhunt.timerpaused = false;
    }

    private void sendActionbar(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}
