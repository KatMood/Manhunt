package de.katmood.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.katmood.manhunt.Manhunt;

public class WorldManager implements Listener{
	
	
	public static String mainWorldNamePath = "MAIN_WORLD";
	public static String mainWorldName = "world";
	
	public static void saveMainWorld() {
		Manhunt.plugin.getConfig().set(mainWorldNamePath, mainWorldName);
		Manhunt.plugin.saveConfig();
	}
	
	public static void loadMainWorld() {
		if(Manhunt.plugin.getConfig().isSet(mainWorldNamePath))
			mainWorldName = Manhunt.plugin.getConfig().getString(mainWorldNamePath);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		e.getPlayer().teleport(new Location(Bukkit.getWorld(mainWorldName), e.getPlayer().getLocation().getX(), e.getPlayer().getLocation().getY(), e.getPlayer().getLocation().getZ()));
	}

	public static void createBackup(String BackupWorldName, World toBackup) {
		WorldCreator wc = new WorldCreator(BackupWorldName);
		wc.copy(toBackup);
		wc.createWorld();
	}
	
	public static void deleteWorld(String worldName) {
		Bukkit.getWorld(worldName).getWorldFolder().delete();
	}
	
	public static World createNewWorld(String worldName) {
		WorldCreator wc = new WorldCreator(worldName);
		wc.type(WorldType.NORMAL);
		wc.generateStructures(true);
		World createdWorld = wc.createWorld();
		
		return createdWorld;
	}
	
	public static void setWorldAsMainWorld(String worldName) {
		mainWorldName = worldName;
	}
	
	
	public static void resetActiveWorld() {
		//Alle Spieler Kicken
		//Alle OfflinePlayers in HashMap NeedWorldUpdate auf true setzen???
	}
	
	
}
