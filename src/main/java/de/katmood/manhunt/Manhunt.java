package de.katmood.manhunt;

import de.katmood.commands.*;
import de.katmood.events.PlayerChatEvent;
import de.katmood.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Manhunt extends JavaPlugin {

    public static JavaPlugin plugin;

    public static HashMap<String, Boolean> Hunted = new HashMap<>();
    public static HashMap<String, Boolean> Alive = new HashMap<>();
    public static HashMap<String, Boolean> NeedWorldUpdate = new HashMap<>();

    public static ArrayList<String> getHunteds() {
        ArrayList<String> hunteds = new ArrayList<>();
        for(String ck : Hunted.keySet()) {
            if(Hunted.get(ck))
                hunteds.add(ck);
        }
        return hunteds;
    }

    public static ArrayList<String> getHunters() {
        ArrayList<String> hunters = new ArrayList<>();
        for(String ck : Hunted.keySet()) {
            if(!Hunted.get(ck))
                hunters.add(ck);
        }
        return hunters;
    }

    public static ArrayList<String> getOnlineHunters() {
        ArrayList<String> hunters = new ArrayList<>();
        for(String ck : Hunted.keySet()) {
            if(!Hunted.get(ck)) {
                if(Bukkit.getPlayer(ck) != null)
                    hunters.add(ck);
            }

        }
        return hunters;
    }

    public static Timer timer = new Timer();

    public static Inventory huntedinv = Bukkit.createInventory(null, 9*3, "§a§lTeam Inventar");
    public static Inventory hunterinv = Bukkit.createInventory(null, 9*3, "§a§lTeam Inventar");

    public static String prefix = "§7[§bKat§7] ";
    public static String lprefix = "§7§l[[§b§lKat§7§l] ";
    public static String noplayer = prefix+"§cNur Spieler können das tun!";
    public static String HuntPath = "Hunted";
    public static String pdata = "PLAYERDATA";
    public static String Timer = "TIMER";
    public static String Teamops = "TeamOptions";
    public static String AlivePath = "Alive";
    public static String game = "GAME";
    public static String Started = "Started";
    public static String NWUPath = "NeedWorldUpdate";
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
    public static boolean started = false;

    public static int time;

    public static void saveNeedWorldUpdate() {
        for(OfflinePlayer cp : Bukkit.getOfflinePlayers()){
            if(NeedWorldUpdate.containsKey(cp.getName()))
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+NWUPath, NeedWorldUpdate.get(cp.getName()));
            else
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+NWUPath, true);
        }
    }

    public static void loadNeedWorldUpdate() {

        Set<String> childs = Manhunt.plugin.getConfig().getConfigurationSection(pdata).getKeys(false);

        for(String cc : childs) {
            Boolean nwu = plugin.getConfig().getBoolean(pdata+"."+cc+"."+NWUPath);
            NeedWorldUpdate.put(cc, nwu);
        }

    }

    public static void saveStarted() {
        plugin.getConfig().set(game+"."+Started, started);
        plugin.saveConfig();
    }

    public static void loadStarted() {
        started = plugin.getConfig().getBoolean(game+"."+Started);
    }

    public static void saveAlive() {
        for(OfflinePlayer cp : Bukkit.getOfflinePlayers()){
            if(Alive.containsKey(cp.getName()))
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+AlivePath, Alive.get(cp.getName()));
            else
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+AlivePath, true);
        }

        plugin.saveConfig();

    }

    public static void loadAlive() {

        Set<String> childs = Manhunt.plugin.getConfig().getConfigurationSection(pdata).getKeys(false);

        for(String cc : childs) {
            Boolean alive = plugin.getConfig().getBoolean(pdata+"."+cc+"."+AlivePath);
            Alive.put(cc, alive);
        }
    }

    public static void saveTeamConfig(){
        plugin.getConfig().set(Teamops+".TeamTeleport", ttp);
        plugin.getConfig().set(Teamops+".TeamInventory", tinv);
        plugin.getConfig().set(Teamops+".TeamChat", tchat);
        plugin.saveConfig();
    }

    public static void loadTeamConfig() {
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
        loadStarted();
        loadHunted();
        loadAlive();
        loadNeedWorldUpdate();
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
        getCommand("teaminventory").setExecutor(new TeamInventoryCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MenuCommand(), this);
        pm.registerEvents(new PlayerChatEvent(), this);
        pm.registerEvents(new TimerOptionsGUI(), this);
        pm.registerEvents(new ManhuntSetCommand(), this);
        pm.registerEvents(new TeamOptionsGUI(), this);

    }

    @Override
    public void onDisable() {
       saveStarted();
       saveHunted();
       saveAlive();
       saveNeedWorldUpdate();
       saveTeamConfig();
       saveTimer();
    }
}
