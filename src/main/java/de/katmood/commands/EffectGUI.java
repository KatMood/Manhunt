package de.katmood.commands;

import java.util.ArrayList;
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
import org.bukkit.inventory.meta.ItemMeta;
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
	
	
	
	public static final String TEAM_HUNTERS = "HUNTERS", TEAM_HUNTED = "HUNTED";
	
	
	public static String TEAM_SELECTION_HUNTER_TEXT = ChatColor.RED+"Jäger";
	public static String TEAM_SELECTION_HUNTED_TEXT = ChatColor.GREEN+"Gejagte";
	
	static HashMap<PotionEffectType, Integer> hunterEffects = new HashMap<>();
	static HashMap<PotionEffectType, Integer> huntedEffects = new HashMap<>();
	
	
	static ItemStack noneItem() {
		ItemStack none = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta none_meta = none.getItemMeta();
        none_meta.setDisplayName(" ");
        none.setItemMeta(none_meta);
        return none;
	}
	
	
	//Wichtig: Es müssen 4 Effekte Ausgelassen werden, sonst wird das Inventar zu groß und crasht!
	static final PotionEffectType[] toIgnorePotionEffects = new PotionEffectType[] {PotionEffectType.LEVITATION, PotionEffectType.POISON, PotionEffectType.WITHER, PotionEffectType.BAD_OMEN};
	
	@EventHandler
	public void onInventroyClick(InventoryClickEvent e) {
		String invTitle = e.getView().getTitle();
		ItemStack clicked = e.getCurrentItem();
		
		if(invTitle.equalsIgnoreCase(ChatColor.BLUE+"Teamefekte")) {
			
			
			
		}
		
		if(invTitle.equalsIgnoreCase(ChatColor.BLUE+"Teamauswahl")) {
			if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(TEAM_SELECTION_HUNTER_TEXT))
				e.getWhoClicked().openInventory(generateEveryEffectInventory(TEAM_HUNTERS));
			if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(TEAM_SELECTION_HUNTED_TEXT))
				e.getWhoClicked().openInventory(generateEveryEffectInventory(TEAM_HUNTED));
			
		}
		
		
	}
	

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
		Inventory toReturn = Bukkit.createInventory(null, 3*9,  ChatColor.BLUE+"Teamauswahl");
		
		
		ItemStack huterButton = new ItemStack(Material.RED_STAINED_GLASS_PANE);
		ItemMeta hunterButtonMeta = huterButton.getItemMeta();
		hunterButtonMeta.setDisplayName(TEAM_SELECTION_HUNTER_TEXT);
		huterButton.setItemMeta(hunterButtonMeta);
		
		ItemStack hutedButton = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
		ItemMeta huntedButtonMeta = hutedButton.getItemMeta();
		huntedButtonMeta.setDisplayName(TEAM_SELECTION_HUNTED_TEXT);
		hutedButton.setItemMeta(huntedButtonMeta);
		
		
		for(int i = 0; i<3*9;i++)
			toReturn.setItem(i, noneItem());
		
		toReturn.setItem(13, huterButton);
		toReturn.setItem(15, hutedButton);
		
		return toReturn;
	}
	
	
	
	public static boolean shouldIgnorePotionEffect(PotionEffectType pt) {
		for(PotionEffectType cpt : toIgnorePotionEffects) {
			if(cpt.getId()==pt.getId())
				return true;
		}
		return false;
	}
	
	static Inventory generateEveryEffectInventory(String team) {
		
		
		
		
		PotionEffectType[] potionEffectTypes = new PotionEffectType[PotionEffectType.values().length-toIgnorePotionEffects.length];
		
		int inIterator = 0;
		for(int i = 0; i< PotionEffectType.values().length;i++) {
			PotionEffectType cpe = PotionEffectType.values()[i];
			if(!shouldIgnorePotionEffect(cpe)) {
				potionEffectTypes[inIterator]=cpe;
				inIterator++;
			}
				
			
		}
		
		
		ArrayList<ItemStack> toInv = new ArrayList<>();
		
		boolean calc = true;
		int potIndex = 0;
		int iteration = 0;
		
		
		//Building Inventory with every potionType inside and walls form noneItem
		while(calc) {
			iteration++;
			boolean pot = true;
			
			if(iteration <= 9)
				pot = false;
			if(iteration%9==0)
				pot = false;
			if(iteration%9==1)
				pot = false;
			
			if(pot) {
				if(potIndex < potionEffectTypes.length) {
					PotionEffectType ci = potionEffectTypes[potIndex];
					
					PotionEffectType cpt = ci;
					int currentEffectLevel = -1;
					if(getTeamEffectsByTeamID(team).containsKey(cpt)) 
						currentEffectLevel = getTeamEffectsByTeamID(team).get(cpt);
					ItemStack currentPotion = new ItemStack(Material.POTION, 1);
					PotionMeta currentPotionMeta = (PotionMeta) currentPotion.getItemMeta();
					currentPotionMeta.addCustomEffect(new PotionEffect(cpt, 1, 1), false);
					currentPotionMeta.setColor(cpt.getColor());
					if(currentEffectLevel == 0) {//lvl: 0 (Effekt aus)
						currentPotionMeta.setDisplayName(ChatColor.RED+"(lvl:"+currentEffectLevel+")");
					}else {		//lvl: >0 (Effekt an)
						currentPotionMeta.setDisplayName(ChatColor.GREEN+"(lvl:"+currentEffectLevel+")");
						currentPotionMeta.addEnchant(Enchantment.DURABILITY, 1, true);
					}
					currentPotion.setItemMeta(currentPotionMeta);
					
					toInv.add(currentPotion);
					potIndex++;
				}else {
					while(!(iteration%9==2)) {
						iteration++;
						toInv.add(noneItem());
					}
					for(int i = 0; i<9;i++)
						toInv.add(noneItem());
					calc = false;
					break;
				}
			}else
				toInv.add(noneItem());
		}
		
		
		Inventory toReturn = Bukkit.createInventory(null, 9*6, ChatColor.BLUE+"Teamefekte");
		
		for(int i = 0; i<toInv.size();i++) {
			if(i<54)
				toReturn.setItem(i, toInv.get(i));
		}
		
		return toReturn;
	}	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		Player p = (Player)sender;
		
		p.openInventory(selectTeamInventory());
		
		
		return false;
	}
}

class InvalidTeamIDException extends Exception{
	public InvalidTeamIDException() {
		super("Warning: Invalid TeamID!");
	}
}
