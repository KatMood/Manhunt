package de.katmood.manhunt;

import de.katmood.commands.*;
import de.katmood.events.PlayerChatEvent;
import de.katmood.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Manhunt extends JavaPlugin {

    public static JavaPlugin plugin;

    public static HashMap<String, Boolean> Hunted = new HashMap<>();

    public static ArrayList<String> getHuteds() {
        ArrayList<String> huteds = new ArrayList<>();
        for(String ck : Hunted.keySet()) {
            if(Hunted.get(ck))
                huteds.add(ck);
        }
        return huteds;
    }

    public static Timer timer = new Timer();

    public static String prefix = "§7[§bKat§7] ";
    public static String lprefix = "§7§l[[§b§lKat§7§l] ";
    public static String noplayer = prefix+"§cNur Spieler können das tun!";
    public static String HuntPath = "Hunted";
    public static String pdata = "PLAYERDATA";
    public static String Timer = "TIMER";
    public static String Teamops = "TeamOptions";
    public static String shortInteger(int duration) {
        String string = "";
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        if (duration / 60 / 60 / 24 >= 1) {
            duration -= duration / 60 / 60 / 24 * 60 * 60 * 24;
        }
        if (duration / 60 / 60 >= 1) {
            hours = duration / 60 / 60;
            duration -= duration / 60 / 60 * 60 * 60;
        }
        if (duration / 60 >= 1) {
            minutes = duration / 60;
            duration -= duration / 60 * 60;
        }
        if (duration >= 1)
            seconds = duration;
        if (hours <= 9) {
            string = String.valueOf(string) + "0" + hours + ":";
        } else {
            string = String.valueOf(string) + hours + ":";
        }
        if (minutes <= 9) {
            string = String.valueOf(string) + "0" + minutes + ":";
        } else {
            string = String.valueOf(string) + minutes + ":";
        }
        if (seconds <= 9) {
            string = String.valueOf(string) + "0" + seconds;
        } else {
            string = String.valueOf(string) + seconds;
        }
        return string;
    }

    public static boolean teamchat = true;
    public static boolean timerenabled = true;
    public static boolean timerpaused = false;
    public static boolean ttp = true;
    public static boolean tinv = true;
    public static boolean tchat = true;

    public static int time;

    public static void saveTeamConfig(){
        plugin.getConfig().set(Teamops+".TeamTeleport", ttp);
        plugin.getConfig().set(Teamops+".TeamInventory", tinv);
        plugin.getConfig().set(Teamops+".TeamChat", tchat);
        plugin.saveConfig();
    }

    public static void loadTeamConfig(){
        ttp = plugin.getConfig().getBoolean(Teamops+".TeamTeleport");
        tinv = plugin.getConfig().getBoolean(Teamops+".TeamInventory");
        tchat = plugin.getConfig().getBoolean(Teamops+".TeamChat");
    }

    public static void saveTimer() {

        plugin.getConfig().set(Timer+".Enabled", timerenabled);
        plugin.getConfig().set(Timer+".Paused", timerpaused);
        plugin.getConfig().set(Timer+".Time", time);
        plugin.saveConfig();

    }

    public static void loadTimer() {
        timerenabled = plugin.getConfig().getBoolean(Timer+".Enabled");
        timerpaused = plugin.getConfig().getBoolean(Timer+".Paused");
        time = plugin.getConfig().getInt(Timer+".Time");
    }

    public static void saveHunted() {

        for(OfflinePlayer cp : Bukkit.getOfflinePlayers()){
            if(Hunted.containsKey(cp.getName()))
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+HuntPath, Hunted.get(cp.getName()));
            else
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+HuntPath, false);
        }

        plugin.saveConfig();

    }

    public static void loadHunted() {

        Set<String> childs = Manhunt.plugin.getConfig().getConfigurationSection(pdata).getKeys(false);

        for(String cc : childs){
            Boolean hunted = plugin.getConfig().getBoolean(pdata+"."+cc+"."+HuntPath);
            Hunted.put(cc, hunted);
        }


    }

    @Override
    public void onEnable() {
        plugin=this;
        loadHunted();
        loadTimer();
        loadTeamConfig();

        getCommand("manhuntset").setExecutor(new ManhuntSetCommand());
        getCommand("manhuntsetgui").setExecutor(new ManhuntSetCommand());
        getCommand("manhuntlist").setExecutor(new ManhuntListCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("menu").setExecutor(new MenuCommand());
        getCommand("timeroptionsgui").setExecutor(new TimerOptionsGUI());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("manhuntdelete").setExecutor(new ManhuntSetCommand());
        getCommand("teamoptionsgui").setExecutor(new TeamOptionsGUI());
        getCommand("effectgui").setExecutor(new EffectGUI());
        getCommand("teamteleport").setExecutor(new TeamTeleportCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MenuCommand(), this);
        pm.registerEvents(new PlayerChatEvent(), this);
        pm.registerEvents(new TimerOptionsGUI(), this);
        pm.registerEvents(new ManhuntSetCommand(), this);
        pm.registerEvents(new TeamOptionsGUI(), this);

    }

    @Override
    public void onDisable() {
       saveHunted();
       saveTeamConfig();
       saveTimer();
    }
}
