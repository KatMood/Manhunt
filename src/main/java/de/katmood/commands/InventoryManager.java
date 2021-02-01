package de.katmood.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.katmood.manhunt.Manhunt;

public class InventoryManager {
	
	public static void saveInv(String path, Inventory toSave) {
		ArrayList<ItemStack> itemStacks = new ArrayList<>();
		ItemStack[] contents = toSave.getContents();
		for(ItemStack cis : contents)
			if(cis!=null)
				itemStacks.add(cis);
	
		Manhunt.plugin.getConfig().set(path, itemStacks);
		Manhunt.plugin.saveConfig();
	}
	
	public static Inventory loadInv(String configurationPath) {
		Inventory teamInv = Bukkit.createInventory(null, 3*9);
		if(Manhunt.plugin.getConfig().isSet(configurationPath)) {
			ItemStack[] contents = teamInv.getContents();
			List<?> toLoadInv = Manhunt.plugin.getConfig().getList(configurationPath);
			for(int i = 0; i<toLoadInv.size();i++) {
				System.out.println("Loading... "+toLoadInv.get(i));
				contents[i] = (ItemStack) toLoadInv.get(i);
			}	
			teamInv.setContents(contents);
		}
		return teamInv;
	}
	
}
