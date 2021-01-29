package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class TimerOptionsGUI implements Listener, CommandExecutor {

    static void rernderInv(Inventory GUI){

        ItemStack none = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack enable = new ItemStack(Material.REDSTONE);
        ItemStack disable = new ItemStack(Material.REDSTONE);
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemStack back = new ItemStack(Material.ARROW);

        ItemMeta none_meta = none.getItemMeta();
        none_meta.setDisplayName(" ");
        none.setItemMeta(none_meta);

        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName("§cClose");
        close.setItemMeta(close_meta);

        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName("§fBack");
        back.setItemMeta(back_meta);

        ItemMeta enable_meta = enable.getItemMeta();
        enable_meta.setDisplayName("§5§lTimer §7§l¦ §a§lAktiv");
        ArrayList<String> enable_lore = new ArrayList<>();
        enable_lore.add("§eDer Timer ist aktiv.");
        enable_lore.add("§eKlicke um ihn zu");
        enable_lore.add("§edeaktivieren.");
        enable_meta.setLore(enable_lore);
        enable_meta.addEnchant(Enchantment.DURABILITY, 0, true);
        enable_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        enable.setItemMeta(enable_meta);

        ItemMeta disable_meta = disable.getItemMeta();
        disable_meta.setDisplayName("§5§lTimer §7§l¦ §c§lInaktiv");
        ArrayList<String> disable_lore = new ArrayList<>();
        disable_lore.add("§eDer Timer ist inaktiv.");
        disable_lore.add("§eKlicke um ihn zu");
        disable_lore.add("§eaktivieren.");
        disable_meta.setLore(disable_lore);
        disable.setItemMeta(disable_meta);

        GUI.setItem(0, none);
        GUI.setItem(1, none);
        GUI.setItem(2, none);
        GUI.setItem(3, none);
        GUI.setItem(4, none);
        GUI.setItem(5, none);
        GUI.setItem(6, none);
        GUI.setItem(7, none);
        GUI.setItem(8, none);
        GUI.setItem(9, none);
        GUI.setItem(10, none);
        GUI.setItem(11, none);
        GUI.setItem(12, none);
        if(Manhunt.timerenabled)
            GUI.setItem(13, enable);
        if(!Manhunt.timerenabled)
            GUI.setItem(13, disable);
        GUI.setItem(14, none);
        GUI.setItem(15, none);
        GUI.setItem(16, none);
        GUI.setItem(17, none);
        GUI.setItem(18, none);
        GUI.setItem(19, none);
        GUI.setItem(20, none);
        GUI.setItem(21, back);
        GUI.setItem(22, close);
        GUI.setItem(23, none);
        GUI.setItem(24, none);
        GUI.setItem(25, none);
        GUI.setItem(26, none);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equalsIgnoreCase("§5§lTimer Options")) {
            e.setCancelled(true);
            if(e.getCurrentItem().hasItemMeta()){
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5§lTimer §7§l¦ §a§lAktiv")) {
                    Manhunt.timerenabled = false;
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§5§lTimer §7§l¦ §c§lInaktiv")) {
                    Manhunt.timerenabled = true;
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")){
                    p.closeInventory();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fBack")){
                    p.closeInventory();
                    p.performCommand("menu");
                }
                rernderInv(e.getInventory());
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;

            Inventory GUI = Bukkit.createInventory(null, 9*3, "§5§lTimer Options");

            p.openInventory(GUI);

            rernderInv(GUI);

        } else
            sender.sendMessage(Manhunt.noplayer);
        return false;
    }
}
