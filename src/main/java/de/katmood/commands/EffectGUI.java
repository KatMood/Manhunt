package de.katmood.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;
/*
 * Infos:
 * -Es gibt 1 - 32 (32.) Effects
 * 
 */


public class EffectGUI implements CommandExecutor{

	
	
	
	
	public static Inventory generateEveryEffectInventory() {
		Inventory toReturn = Bukkit.createInventory(null, 9*3);
		
		
		int index = 0;
		for(PotionEffectType cpt : PotionEffectType.values()) {
			index++;
			ItemStack currentPotion = new ItemStack(Material.POTION, 1);
			PotionMeta currentPotionMeta = (PotionMeta) currentPotion.getItemMeta();
			currentPotionMeta.addCustomEffect(new PotionEffect(cpt, 1, 1), false);
			currentPotionMeta.setDisplayName(ChatColor.MAGIC+""+cpt.getName()+" Effect!");
			currentPotion.setItemMeta(currentPotionMeta);
			toReturn.setItem(index, currentPotion);
		}
		return toReturn;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		Player p = (Player)sender;
		
		p.openInventory(generateEveryEffectInventory());
		
		
		
		
		
		
		return false;
	}

	
	
	
	
}
