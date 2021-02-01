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

public class DeathOptionsGUI implements Listener, CommandExecutor {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String invname = e.getView().getTitle();
        String itemname = e.getCurrentItem().getItemMeta().getDisplayName();

        if(invname.equalsIgnoreCase("§c§lTodes Optionen")) {
            e.setCancelled(true);
            if(itemname.equalsIgnoreCase("§7§lWie viele")) {
                renderWieViele(e.getInventory());
            }
        }
        if(invname.equalsIgnoreCase("§c§lTodes Optionen")) {
            e.setCancelled(true);
            if(itemname.equalsIgnoreCase("§7§lAlle")) {
                Manhunt.kill_all = true;
            }
            if(itemname.equalsIgnoreCase("§7§lEiner")) {
                Manhunt.kill_all = false;
            }
            renderWieViele(e.getInventory());
        }

    }

    public void renderMain(Inventory inv) {

        ItemStack howmany = new ItemStack(Material.SKELETON_SKULL);

        ItemMeta howmany_meta = howmany.getItemMeta();
        howmany_meta.setDisplayName("§7§lWie viele");
        ArrayList<String> howmany_lore = new ArrayList<>();
        howmany_lore.add("§eKlicke um einzustellen,");
        howmany_lore.add("§ewie viele Gejagte sterben");
        howmany_lore.add("§emüssen, um das Spiel");
        howmany_lore.add("§ezu beenden.");
        howmany_meta.setLore(howmany_lore);
        howmany.setItemMeta(howmany_meta);

        Manhunt.setItemNone(inv, 9*5);

        inv.setItem(22, howmany);

    }

    public void renderWieViele(Inventory inv) {

        ItemStack one = new ItemStack(Material.PAPER);
        ItemStack all = new ItemStack(Material.PAPER);

        ItemMeta one_meta = one.getItemMeta();
        one_meta.setDisplayName("§7§lEiner");
        ArrayList<String> one_lore = new ArrayList<>();
        one_lore.add("§eNur ein gejagter Spieler");
        one_lore.add("§emuss sterben, damit das");
        one_lore.add("§eSpiel gestoppt wird.");
        one_meta.setLore(one_lore);
        if(!Manhunt.kill_all) {
            one_meta.addEnchant(Enchantment.DURABILITY, 0, true);
            one_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        one.setItemMeta(one_meta);

        ItemMeta all_meta = all.getItemMeta();
        all_meta.setDisplayName("§7§lAlle");
        ArrayList<String> all_lore = new ArrayList<>();
        all_lore.add("§eAlle gejagten Spieler müssen");
        all_lore.add("§esterben, damit das");
        all_lore.add("§eSpiel gestoppt wird.");
        all_meta.setLore(all_lore);
        if(Manhunt.kill_all) {
            all_meta.addEnchant(Enchantment.DURABILITY, 0, true);
            all_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        all.setItemMeta(all_meta);


        Manhunt.setItemNone(inv, 9*5);

        inv.setItem(20, one);
        inv.setItem(24, all);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;
        Inventory gui = Bukkit.createInventory(null, 9*5, "§c§lTodes Optionen");

        renderMain(gui);

        p.openInventory(gui);

        return false;
    }
}
