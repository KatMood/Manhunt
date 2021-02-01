package de.katmood.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

import de.katmood.manhunt.Manhunt;




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
	
	
	public static void applyEffectsToEveryPlayer() {
		for(PotionEffectType currentPotionType : huntedEffects.keySet()) {
			for(String currentPlayer : Manhunt.getHunteds())
				if(huntedEffects.get(currentPotionType)>0)
					Bukkit.getPlayer(currentPlayer).addPotionEffect(new PotionEffect(currentPotionType,99999, huntedEffects.get(currentPotionType)-1), true);
		}
		
		for(PotionEffectType currentPotionType : hunterEffects.keySet()) {
			for(String currentPlayer : Manhunt.getHunters())
				if(hunterEffects.get(currentPotionType)>0)
					Bukkit.getPlayer(currentPlayer).addPotionEffect(new PotionEffect(currentPotionType,99999, hunterEffects.get(currentPotionType)-1), true);
		}
		
	}
	
	public static void removeEffectsFromEveryPlayer() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			for(PotionEffectType cpt : PotionEffectType.values())
			p.removePotionEffect(cpt);
		}
		
	}
	
	
	static String effectListPath = "EFFECT_LIST";
	public static void saveEffectLevelsInConfig() {
		fillPotionEffectLists();
		for(PotionEffectType cpt : huntedEffects.keySet()) {
			if(cpt != null) {
				Manhunt.plugin.getConfig().set(effectListPath+"."+TEAM_HUNTED+"."+cpt.getName(), huntedEffects.get(cpt));
			}
		}
		for(PotionEffectType cpt : hunterEffects.keySet()) {
			if(cpt != null)
				Manhunt.plugin.getConfig().set(effectListPath+"."+TEAM_HUNTERS+"."+cpt.getName(), hunterEffects.get(cpt));
		}
		Manhunt.plugin.saveConfig();
	}
	
	public static void loadEffectLevelsFromConfig() {
		fillPotionEffectLists();
		if(Manhunt.plugin.getConfig().isSet(effectListPath)) {
			for(String c : Manhunt.plugin.getConfig().getConfigurationSection(effectListPath+"."+TEAM_HUNTED).getKeys(false)) {
				huntedEffects.put(PotionEffectType.getByName(c), Manhunt.plugin.getConfig().getInt(effectListPath+"."+TEAM_HUNTED+"."+c));
			}
			for(String c : Manhunt.plugin.getConfig().getConfigurationSection(effectListPath+"."+TEAM_HUNTERS).getKeys(false)) {
				hunterEffects.put(PotionEffectType.getByName(c), Manhunt.plugin.getConfig().getInt(effectListPath+"."+TEAM_HUNTERS+"."+c));
			}
		}else {
			System.out.println("Warning: Could not laod EffectList Settings, because it was not set in the config.yml!");
		}
			
	}
	
	
	
	@EventHandler
	public void onInventroyClick(InventoryClickEvent e) {
		String invTitle = e.getView().getTitle();
		ItemStack clicked = e.getCurrentItem();
		if(clicked!=null) {
		if(invTitle.startsWith(ChatColor.GOLD+"Levelauswahl")) {
			e.setCancelled(true);
			PotionMeta pm = (PotionMeta) e.getInventory().getItem(11).getItemMeta();
			PotionEffectType currentType = pm.getCustomEffects().get(0).getType();
			String teamID = IntToTeamID(Integer.valueOf(invTitle.replace(ChatColor.GOLD+"Levelauswahl_", "")));
			
			if(clicked.equals(plusButton())) {
				//e.getWhoClicked().sendMessage("plus... [teamID: "+teamID+" | type: "+currentType.getName()+"]");
				if(getPotionLevelByTeam(currentType, teamID)<maxLevel)
					setEffectLevel(teamID, currentType, getPotionLevelByTeam(currentType, teamID)+1);
			}
			if(clicked.equals(minusButton())) {
				//e.getWhoClicked().sendMessage("minus... [teamID: "+teamID+" | type: "+currentType.getName()+"]");
				if(getPotionLevelByTeam(currentType, teamID)>0)
					setEffectLevel(teamID, currentType, getPotionLevelByTeam(currentType, teamID)-1);
			}
			drawLevelSelectInventory(e.getInventory(), currentType, teamID);
		}
		
		if(invTitle.startsWith(ChatColor.BLUE+"Teamefekte")) {
			e.setCancelled(true);
			PotionMeta currentMeta = (PotionMeta)clicked.getItemMeta();
			e.getWhoClicked().openInventory(selectLevelInventory(currentMeta.getCustomEffects().get(0).getType(), IntToTeamID(Integer.valueOf(invTitle.replace(ChatColor.BLUE+"Teamefekte_", "")))));	//Assuming the clicked Potion has only one Custom Effect
		}
		
		if(invTitle.equalsIgnoreCase(ChatColor.BLUE+"Teamauswahl")) {
			e.setCancelled(true);
			if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(TEAM_SELECTION_HUNTER_TEXT))
				e.getWhoClicked().openInventory(selectEffectInventory(TEAM_HUNTERS));
			if(clicked.getItemMeta().getDisplayName().equalsIgnoreCase(TEAM_SELECTION_HUNTED_TEXT))
				e.getWhoClicked().openInventory(selectEffectInventory(TEAM_HUNTED));
			
		}
		
		}
	}
	
	
	
	public static final String TEAM_HUNTERS = "HUNTERS", TEAM_HUNTED = "HUNTED";
	
	
	public static String TEAM_SELECTION_HUNTER_TEXT = ChatColor.RED+"Jäger";
	public static String TEAM_SELECTION_HUNTED_TEXT = ChatColor.GREEN+"Gejagte";
	
	public static final int maxLevel = 64;
	
	//Wichtig: Es m�ssen 4 Effekte Ausgelassen werden, sonst wird das Inventar zu gro� und crasht!
	static final PotionEffectType[] toIgnorePotionEffects = new PotionEffectType[] {PotionEffectType.LEVITATION, PotionEffectType.POISON, PotionEffectType.WITHER, PotionEffectType.BAD_OMEN};
	
	static HashMap<PotionEffectType, Integer> hunterEffects = new HashMap<>();
	static HashMap<PotionEffectType, Integer> huntedEffects = new HashMap<>();
	
	static ItemStack minusButton() {
		ItemStack toReturn = new ItemStack(Material.BLACK_DYE);
		ItemMeta meta = toReturn.getItemMeta();
		meta.setDisplayName(ChatColor.BOLD+"-1");
		toReturn.setItemMeta(meta);
		return toReturn;
	}
	
	static ItemStack plusButton() {
		ItemStack toReturn = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = toReturn.getItemMeta();
		meta.setDisplayName(ChatColor.BOLD+"+1");
		toReturn.setItemMeta(meta);
		return toReturn;
	}
	
	static ItemStack showLevelItemStack(PotionEffectType type, String teamID) {
		ItemStack toReturn;
		if(getPotionLevelByTeam(type, teamID)==0) {
			toReturn = new ItemStack(Material.RED_STAINED_GLASS_PANE);
			ItemMeta meta = toReturn.getItemMeta();
			meta.setDisplayName("Nicht Aktiv!");
			toReturn.setItemMeta(meta);
		}else {
			toReturn = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
			ItemMeta meta = toReturn.getItemMeta();
			meta.setDisplayName(String.valueOf(getPotionLevelByTeam(type, teamID)));
			toReturn.setAmount(getPotionLevelByTeam(type, teamID));
			toReturn.setItemMeta(meta);
		}
		
		
		return toReturn;
	}
	
	static PotionEffectType[] allNotToIgnorePotionEffects() {
		PotionEffectType[] potionEffectTypes = new PotionEffectType[PotionEffectType.values().length-toIgnorePotionEffects.length];
		
		int inIterator = 0;
		for(int i = 0; i< PotionEffectType.values().length;i++) {
			PotionEffectType cpe = PotionEffectType.values()[i];
			if(!shouldIgnorePotionEffect(cpe)) {
				potionEffectTypes[inIterator]=cpe;
				inIterator++;
			}
		}
		return potionEffectTypes;
	}
	
	static void fillPotionEffectLists() {
		for(PotionEffectType cpt : allNotToIgnorePotionEffects()) {
			if(!huntedEffects.containsKey(cpt))
				huntedEffects.put(cpt, 0);
			if(!hunterEffects.containsKey(cpt))
				hunterEffects.put(cpt, 0);
		}
	}
	
	static int TeamIDToInt(String teamID) {
		if(teamID.equalsIgnoreCase(TEAM_HUNTERS))
			return 0;
		else if(teamID.equalsIgnoreCase(TEAM_HUNTED))
			return 1;
		try {throw new InvalidTeamIDException();} catch (InvalidTeamIDException e) {e.printStackTrace();}
		return -1;
	}
	
	static String IntToTeamID(int value) {
		if(value==0)
			return TEAM_HUNTERS;
		else if(value==1)
			return TEAM_HUNTED;
		return "ERROR!";
	}
	
	static ItemStack noneItem() {
		ItemStack none = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta none_meta = none.getItemMeta();
        none_meta.setDisplayName(" "); 
        none.setItemMeta(none_meta);
        return none;
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
	static int getPotionLevelByTeam(PotionEffectType potionType, String teamID) {
		if(getTeamEffectsByTeamID(teamID).containsKey(potionType)) 
			return getTeamEffectsByTeamID(teamID).get(potionType);
		return -1;
	}
	
	static ItemStack generatePotionRepresentator(PotionEffectType potionType, String teamID) {
		ItemStack TypePototion = new ItemStack(Material.POTION, 1);
		PotionMeta tpm = (PotionMeta)TypePototion.getItemMeta();
		tpm.addCustomEffect(new PotionEffect(potionType, 1, 1), false);
		tpm.setColor(potionType.getColor());
		
		if(getPotionLevelByTeam(potionType, teamID) == 0) {//lvl: 0 (Effekt aus)
			tpm.setDisplayName(ChatColor.RED+"(lvl:"+getPotionLevelByTeam(potionType, teamID)+")");
		}else if(getPotionLevelByTeam(potionType, teamID)>0){		//lvl: >0 (Effekt an)
			tpm.setDisplayName(ChatColor.GREEN+"(lvl:"+getPotionLevelByTeam(potionType, teamID)+")");
		}else {
			tpm.setDisplayName(ChatColor.YELLOW+""+ChatColor.UNDERLINE+"(lvl:"+getPotionLevelByTeam(potionType, teamID)+")[ERROR!]");
		}
		TypePototion.setItemMeta(tpm);
		return TypePototion;
	}
	
	
	static void setEffectLevel(String teamID, PotionEffectType toSet, int level) {
		HashMap<PotionEffectType, Integer> foundMap = getTeamEffectsByTeamID(teamID);
		foundMap.put(toSet, level);
	}
	
	static void drawLevelSelectInventory(Inventory currentInv, PotionEffectType potionType, String teamID) {
		
		for(int i = 0; i<3*9;i++)
			currentInv.setItem(i, noneItem());
		
		
		currentInv.setItem(11, generatePotionRepresentator(potionType, teamID));
		
		currentInv.setItem(5, plusButton());
		currentInv.setItem(14, showLevelItemStack(potionType, teamID));
		currentInv.setItem(23, minusButton());
		
	}
	
	static Inventory selectLevelInventory(PotionEffectType potionType, String teamID) {
		Inventory toReturn = Bukkit.createInventory(null, 3*9,  ChatColor.GOLD+"Levelauswahl_"+String.valueOf(TeamIDToInt(teamID)));
		
		drawLevelSelectInventory(toReturn, potionType, teamID);
		
		return toReturn;
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
		
		toReturn.setItem(12, huterButton);
		toReturn.setItem(14, hutedButton);
		
		return toReturn;
	}
	
	
	
	static boolean shouldIgnorePotionEffect(PotionEffectType pt) {
		for(PotionEffectType cpt : toIgnorePotionEffects) {
			if(cpt.getId()==pt.getId())
				return true;
		}
		return false;
	}
	
	static Inventory selectEffectInventory(String team) {
		
		PotionEffectType[] potionEffectTypes = allNotToIgnorePotionEffects();
		
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
					toInv.add(generatePotionRepresentator(ci, team));
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
		
		
		Inventory toReturn = Bukkit.createInventory(null, 9*6, ChatColor.BLUE+"Teamefekte_"+TeamIDToInt(team));
		
		for(int i = 0; i<toInv.size();i++) {
			if(i<54)
				toReturn.setItem(i, toInv.get(i));
		}
		
		return toReturn;
	}	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		Player p = (Player)sender;
		
		
		//setEffectLevel(IntToTeamID(new Random().nextInt(2)), allNotToIgnorePotionEffects()[new Random().nextInt(allNotToIgnorePotionEffects().length-1)], new Random().nextInt(50));
		
		p.openInventory(selectTeamInventory());
		
		
		return false;
	}
}

class InvalidTeamIDException extends Exception{
	public InvalidTeamIDException() {
		super("Warning: Invalid TeamID!");
	}
}
