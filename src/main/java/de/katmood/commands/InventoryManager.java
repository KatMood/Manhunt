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
	
	public static Inventory loadInv(String configurationPath, Inventory toOverwrite) {
		if(Manhunt.plugin.getConfig().isSet(configurationPath)) {
			ItemStack[] contents = toOverwrite.getContents();
			List<?> toLoadInv = Manhunt.plugin.getConfig().getList(configurationPath);
			for(int i = 0; i<toLoadInv.size();i++) {
				System.out.println("Loading... "+toLoadInv.get(i));
				contents[i] = (ItemStack) toLoadInv.get(i);
			}	
			toOverwrite.setContents(contents);
		}
		return toOverwrite;
	}
	
}
