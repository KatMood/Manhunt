package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
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

import java.util.ArrayList;

public class TeamCommand implements Listener, CommandExecutor {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String invname = e.getView().getTitle();
        String itemname = e.getCurrentItem().getItemMeta().getDisplayName();

        if(invname.equalsIgnoreCase("§b§lTeam")) {
            e.setCancelled(true);
            if(itemname.equalsIgnoreCase("§3§lTeleport")) {
                if(Manhunt.getOnlineHunters().contains(p.getName())) {
                    if(Manhunt.getOnlineHunters().size() == 2) {
                        p.closeInventory();
                        p.performCommand("teamteleport");
                    } else if(Manhunt.getOnlineHunters().size() < 2) {
                        p.sendMessage(Manhunt.prefix+"§cEs gibt zu wenig Leute in deinem Team um dies zu tun!");
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cEs gibt zu viele Leute in deinem Team um dies so zu tun!");
                        p.sendMessage(Manhunt.prefix+"§cBitte mache /teamteleport (Spieler) um dich zu einem TeamMitglied zu teleportieren");
                    }
                }
                if(Manhunt.getHunteds().contains(p.getName())) {
                    if(Manhunt.getHunteds().size() == 2) {
                        p.closeInventory();
                        p.performCommand("teamteleport");
                    } else if(Manhunt.getHunteds().size() < 2) {
                        p.sendMessage(Manhunt.prefix+"§cEs gibt zu wenig Leute in deinem Team um dies zu tun!");
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cEs gibt zu viele Leute in deinem Team um dies so zu tun!");
                        p.sendMessage(Manhunt.prefix+"§cBitte mache /teamteleport (Spieler) um dich zu einem TeamMitglied zu teleportieren");
                    }
                }
            }
            if(itemname.equalsIgnoreCase("§2§lInventar")) {
                p.closeInventory();
                p.performCommand("teaminventory");
            }
        }
    }

    public void renderMain(Inventory inv) {
        ItemStack tp = new ItemStack(Material.ENDER_PEARL);
        ItemStack inventory = new ItemStack(Material.CHEST);

        ItemMeta tp_meta = tp.getItemMeta();
        tp_meta.setDisplayName("§3§lTeleport");
        ArrayList<String> tp_lore = new ArrayList<>();
        tp_lore.add("§eKlicke um dich zu einem");
        tp_lore.add("§eTeampartner zu teleportieren.");
        tp_meta.setLore(tp_lore);
        tp.setItemMeta(tp_meta);

        ItemMeta inv_meta = inventory.getItemMeta();
        inv_meta.setDisplayName("§2§lInventar");
        ArrayList<String> inv_lore = new ArrayList<>();
        inv_lore.add("§eKlicke um dein Team-");
        inv_lore.add("§einventar zu öffnen.");
        inv_meta.setLore(inv_lore);
        inventory.setItemMeta(inv_meta);

        Manhunt.setItemNone(inv, 9*3);

        inv.setItem(11, tp);
        inv.setItem(15, inventory);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;

        Inventory teaminv = Bukkit.createInventory(null, 9*3, "§b§lTeam");

        renderMain(teaminv);

        p.openInventory(teaminv);

        return false;
    }
}
