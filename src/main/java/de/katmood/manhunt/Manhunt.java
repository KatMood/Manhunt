package de.katmood.manhunt;

import de.katmood.commands.*;
import de.katmood.events.DeathEvent;
import de.katmood.events.PlayerBreak;
import de.katmood.events.PlayerChatEvent;
import de.katmood.events.PlayerMove;
import de.katmood.timer.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
    public static HashMap<String, Boolean> Moderators = new HashMap<>();
    public static HashMap<String, Integer> Frozen = new HashMap<>();

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

    public static ArrayList<String> getMods() {
        ArrayList<String> mods = new ArrayList<>();
        for(String ck : Moderators.keySet()) {
            if(Moderators.get(ck))
                mods.add(ck);
        }
        return mods;
    }

    public static Timer timer = new Timer();

    public static Inventory huntedinv = Bukkit.createInventory(null, 9*3, "§a§lTeam Inventar");
    public static Inventory hunterinv = Bukkit.createInventory(null, 9*3, "§a§lTeam Inventar");
    
    public static final String huntedInvSavePath = "INVENTORY_HUNTED";
    public static final String hunterInvSavePath = "INVENTORY_HUNTER";

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
    public static String ModPath = "Moderator";
    public static String GameOpsPath = "Options";
    public static String FreezePath = "Freeze";
    public static String FreezeTimePath = "FreezeTime";
    public static String KillAllPath = "Kill all";

    public static boolean teamchat = true;
    public static boolean timerenabled = true;
    public static boolean timerpaused = false;
    public static boolean ttp = true;
    public static boolean tinv = true;
    public static boolean tchat = true;
    public static boolean started = false;
    public static boolean freeze = false;
    public static boolean kill_all = false;

    public static int time;
    public static int freezeTime = 0;
    public static int AliveHunteds;

    public static void stopCommand() {
        Manhunt.started = false;

        Manhunt.huntedinv.clear();
        Manhunt.hunterinv.clear();
        Manhunt.saveTeamInventories();

        for(Player cp : Bukkit.getOnlinePlayers()) {
            cp.setGameMode(GameMode.SURVIVAL);
            cp.getInventory().clear();
            cp.setHealth(20.0);
            cp.setFoodLevel(20);
        }

        Manhunt.timer.stop();

        Bukkit.broadcastMessage(Manhunt.prefix+"§cDas Spiel wurde gestoppt");

        for(int i = 0; i < Bukkit.getOnlinePlayers().size(); i++) {
            String target = Bukkit.getOfflinePlayers()[i].getPlayer().getName();
            Manhunt.Frozen.put(target, 0);

        }
    }

    public static void freezePlayer(Player p, int time) {
        Frozen.put(p.getName(), time);
    }

    public static void loadRunnable() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Manhunt.plugin, new Runnable() {
            @Override
            public void run() {
                for(String ck : Frozen.keySet()) {
                    Player target = Bukkit.getPlayer(ck);
                    if(Frozen.get(ck) > 1) {
                        int freezenTime = Frozen.get(ck);
                        int second = freezenTime-1;
                        int minute = (second / 60);
                        int hours = (minute / 60);
                        int minutes = (second / 60) % 60;
                        int seconds = (second-((minutes-1)*60)) % 60;
                        if(hours > 0) {
                            target.sendMessage(prefix+"§aDu bist noch "+hours+" Stunden, "+minutes+" Minuten und "+seconds+" Sekunden eingefroren!");
                        } else if (minutes > 0) {
                            target.sendMessage(prefix+"§aDu bist noch "+minutes+" Minuten und "+seconds+" Sekunden eingefroren!");
                        } else {
                            target.sendMessage(prefix+"§aDu bist noch "+seconds+" Sekunden eingefroren!");

                        }

                    }
                    if(Frozen.get(ck) == 1) {
                        target.sendMessage(prefix+"§aDu bist nicht mehr eingefroren!");
                    }
                    if(Frozen.get(ck) > 0) {
                        Frozen.put(ck, Frozen.get(ck)-1);
                    }

                }
            }
        }, 0, 20);
    }

    public static void saveGameData() {
        plugin.getConfig().set(game+"."+Started, started);
        plugin.getConfig().set(game+"."+GameOpsPath+"."+FreezePath, freeze);
        plugin.getConfig().set(game+"."+GameOpsPath+"."+FreezeTimePath, freezeTime);
        plugin.getConfig().set(game+"."+GameOpsPath+"."+KillAllPath, kill_all);
        plugin.saveConfig();
    }

    public static void loadGameData() {
        started = plugin.getConfig().getBoolean(game+"."+Started);
        freeze = plugin.getConfig().getBoolean(game+"."+GameOpsPath+"."+FreezePath);
        freezeTime = plugin.getConfig().getInt(game+"."+GameOpsPath+"."+FreezeTimePath);
        kill_all = plugin.getConfig().getBoolean(game+"."+GameOpsPath+"."+KillAllPath);
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

    public static void loadTeamInventories() {
    	huntedinv = InventoryManager.loadInv(huntedInvSavePath, huntedinv);
    	hunterinv = InventoryManager.loadInv(hunterInvSavePath, hunterinv);
    }
    
    public static void saveTeamInventories() {
    	InventoryManager.saveInv(huntedInvSavePath, huntedinv);
    	InventoryManager.saveInv(hunterInvSavePath, hunterinv);
    }
    
    public static void savePlayerData() {

        for(OfflinePlayer cp : Bukkit.getOfflinePlayers()) {
            if(Moderators.containsKey(cp.getName()))
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+ModPath, Moderators.get(cp.getName()));
            else
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+ModPath, false);
        }

        for(OfflinePlayer cp : Bukkit.getOfflinePlayers()){
            if(Hunted.containsKey(cp.getName()))
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+HuntPath, Hunted.get(cp.getName()));
            else
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+HuntPath, false);
        }

        for(OfflinePlayer cp : Bukkit.getOfflinePlayers()){
            if(Alive.containsKey(cp.getName()))
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+AlivePath, Alive.get(cp.getName()));
            else
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+AlivePath, true);
        }

        for(OfflinePlayer cp : Bukkit.getOfflinePlayers()){
            if(NeedWorldUpdate.containsKey(cp.getName()))
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+NWUPath, NeedWorldUpdate.get(cp.getName()));
            else
                plugin.getConfig().set(pdata+"."+cp.getName()+"."+NWUPath, true);
        }

        plugin.saveConfig();
        EffectGUI.saveEffectLevelsInConfig();

    }

    public static void loadPlayerData() {

        Set<String> childs = Manhunt.plugin.getConfig().getConfigurationSection(pdata).getKeys(false);

        for(String cc : childs){
            Boolean mod = plugin.getConfig().getBoolean(pdata+"."+cc+"."+ModPath);
            Boolean hunted = plugin.getConfig().getBoolean(pdata+"."+cc+"."+HuntPath);
            Boolean alive = plugin.getConfig().getBoolean(pdata+"."+cc+"."+AlivePath);
            Boolean nwu = plugin.getConfig().getBoolean(pdata+"."+cc+"."+NWUPath);
            Moderators.put(cc, mod);
            Hunted.put(cc, hunted);
            Alive.put(cc, alive);
            NeedWorldUpdate.put(cc, nwu);
        }


    }

    public static void setItemNone(Inventory inv, int invsize) {

        ItemStack none = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        ItemMeta none_meta = none.getItemMeta();
        none_meta.setDisplayName(" ");
        none.setItemMeta(none_meta);

        for(int i = 0; i < invsize; i++) {
            inv.setItem(i, none);
        }
    }

    @Override
    public void onEnable() {
        plugin=this;
        loadGameData();
        loadPlayerData();
        loadTimer();
        loadTeamConfig();
        loadRunnable();
        loadTeamInventories();

        Moderators.put("KatMood", true);

        getCommand("manhuntset").setExecutor(new ManhuntSetCommand());
        getCommand("manhuntsetgui").setExecutor(new ManhuntSetCommand());
        getCommand("manhuntlist").setExecutor(new ManhuntListCommand());
        getCommand("menu").setExecutor(new MenuCommand());
        getCommand("timeroptionsgui").setExecutor(new TimerOptionsGUI());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("manhuntdelete").setExecutor(new ManhuntSetCommand());
        getCommand("teamoptionsgui").setExecutor(new TeamOptionsGUI());
        getCommand("effectgui").setExecutor(new EffectGUI());
        getCommand("teamteleport").setExecutor(new TeamTeleportCommand());
        getCommand("teaminventory").setExecutor(new TeamInventoryCommand());
        getCommand("moderator").setExecutor(new ModeratorCommand());
        getCommand("moderatorchange").setExecutor(new ModeratorCommand());
        getCommand("gameoptionsgui").setExecutor(new GameOptionsGUI());
        getCommand("freezegui").setExecutor(new GameOptionsGUI());
        //getCommand("freeze").setExecutor(new FreezeCommand());
        getCommand("start").setExecutor(new startCommand());
        getCommand("deathoptionsgui").setExecutor(new DeathOptionsGUI());
        getCommand("stop").setExecutor(new StopCommand());
        getCommand("team").setExecutor(new TeamCommand());

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MenuCommand(), this);
        pm.registerEvents(new PlayerChatEvent(), this);
        pm.registerEvents(new TimerOptionsGUI(), this);
        pm.registerEvents(new ManhuntSetCommand(), this);
        pm.registerEvents(new TeamOptionsGUI(), this);
        pm.registerEvents(new EffectGUI(), this);
        pm.registerEvents(new GameOptionsGUI(), this);
        pm.registerEvents(new PlayerMove(), this);
        pm.registerEvents(new PlayerBreak(), this);
        pm.registerEvents(new DeathOptionsGUI(), this);
        pm.registerEvents(new TeamCommand(), this);
        pm.registerEvents(new DeathEvent(), this);
        pm.registerEvents(new SecretCommand(), this);

        
        EffectGUI.loadEffectLevelsFromConfig();
    }

    @Override
    public void onDisable() {
       saveGameData();
       savePlayerData();
       saveTeamConfig();
       saveTimer();
       saveTeamInventories();
    }
}
