package de.katmood.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/*
 * Infos:
 * -Es gibt 1 - 32 (32.) Effects
 * 
 * Syntax:
 *-TeamID's ["HUTERS", "HUNTED"]
 * 
 * Author: Noname
 */


public class EffectGUI implements CommandExecutor, Listener{

	
	
	@EventHandler
	public void onInventroyClick(InventoryClickEvent e) {
		String invTitle = e.getView().getTitle();
		
		
	}
	
	
	public static final String TEAM_HUNTERS = "HUNTERS", TEAM_HUNTED = "HUNTED";
	
	
	static HashMap<PotionEffectType, Integer> hunterEffects = new HashMap<>();
	static HashMap<PotionEffectType, Integer> huntedEffects = new HashMap<>();
	
	static HashMap<PotionEffectType, Integer> getTeamEffectsByTeamID(String teamID) {
		if(teamID.equalsIgnoreCase("HUNTERS"))
			return hunterEffects;
		else if(teamID.equalsIgnoreCase("HUNTED"))
			return huntedEffects;
		
		//Invalid TeamID
		try {throw new InvalidTeamIDException();} catch (Exception e) {}
		return huntedEffects;
	}
	

	
	static Inventory selectTeamInventory() {
		Inventory toReturn = Bukkit.createInventory(null, 2,  ChatColor.BLUE+"Teamauswahl");
		
		
		
		
		return toReturn;
	}
	
	static Inventory generateEveryEffectInventory(String team) {
		Inventory toReturn = Bukkit.createInventory(null, 9*4, ChatColor.BLUE+"Effektte für das Team "+team+" zuweisen!");
		
		int index = 0;
		int currentEffectLevel = -1;
		for(PotionEffectType cpt : PotionEffectType.values()) {
			if(getTeamEffectsByTeamID(team).containsKey(cpt)) 
				currentEffectLevel = getTeamEffectsByTeamID(team).get(cpt);
			index++;
			ItemStack currentPotion = new ItemStack(Material.POTION, 1);
			PotionMeta currentPotionMeta = (PotionMeta) currentPotion.getItemMeta();
			currentPotionMeta.addCustomEffect(new PotionEffect(cpt, 1, 1), false);
			currentPotionMeta.setColor(cpt.getColor());
			if(currentEffectLevel == 0) {//lvl: 0 (Effekt aus)
				currentPotionMeta.setDisplayName(ChatColor.RED+""+cpt.getName()+" Effect!");
			}else {		//lvl: >0 (Effekt an)
				currentPotionMeta.setDisplayName(ChatColor.GREEN+""+cpt.getName()+" Effect!");
				currentPotionMeta.addEnchant(Enchantment.DURABILITY, 1, true);
			}
			currentPotion.setItemMeta(currentPotionMeta);
			toReturn.setItem(index, currentPotion);
		}
		return toReturn;
	}	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		Player p = (Player)sender;
		
		p.openInventory(generateEveryEffectInventory(TEAM_HUNTED));
		
		
		return false;
	}
}

class InvalidTeamIDException extends Exception{
	public InvalidTeamIDException() {
		super("Warning: Invalid TeamID!");
	}
}
