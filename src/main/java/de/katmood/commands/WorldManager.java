package de.katmood.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class WorldManager implements Listener{
	
	public static void saveMainWorld() {
		
	}
	
	public static void loadMainWorld() {
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
	}

	public static void createBackup(World toBackup) {
	
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
		
	}
	
	
	public static void resetActiveWorld() {
		//Alle Spieler Kicken
		//Alle OfflinePlayers in HashMap NeedWorldUpdate auf true setzen???
	}
	
	
}
