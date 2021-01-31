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

public class MenuCommand implements Listener, CommandExecutor {


    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equalsIgnoreCase("§a§lMenu")){
            e.setCancelled(true);
            if(e.getCurrentItem().hasItemMeta()){
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")){
                    p.closeInventory();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lTimer Optionen")){
                    p.closeInventory();
                    p.performCommand("timeroptionsgui");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lGejagte auswählen")) {
                    p.closeInventory();
                    p.performCommand("manhuntsetgui");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§b§lTeam Optionen")){
                    p.closeInventory();
                    p.performCommand("teamoptionsgui");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2§lSpieleinstellungen")) {
                    p.closeInventory();
                    p.performCommand("gameoptionsgui");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§d§lEffekte")) {
                    p.closeInventory();
                    p.performCommand("effectgui");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§a§lStart")) {
                    p.closeInventory();
                    p.performCommand("start");
                }
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;

            Inventory menu = Bukkit.createInventory(null, 9*5, "§a§lMenu");

            ItemStack none = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemStack non = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            ItemStack set = new ItemStack(Material.PLAYER_HEAD);
            ItemStack close = new ItemStack(Material.BARRIER);
            ItemStack timer = new ItemStack(Material.CLOCK);
            ItemStack team = new ItemStack(Material.HEART_OF_THE_SEA);
            ItemStack effects = new ItemStack(Material.POTION);
            ItemStack info = new ItemStack(Material.PAPER);
            ItemStack gameops = new ItemStack(Material.COMPARATOR);
            ItemStack start = new ItemStack(Material.LIME_WOOL);

            ItemMeta none_meta = none.getItemMeta();
            none_meta.setDisplayName(" ");
            none.setItemMeta(none_meta);
            non.setItemMeta(none_meta);

            ItemMeta set_meta = set.getItemMeta();
            set_meta.setDisplayName("§c§lGejagte auswählen");
            ArrayList<String> set_lore = new ArrayList<>();
            set_lore.add("§eKlicke um die Gejagten");
            set_lore.add("§eauszuwählen");
            set_meta.setLore(set_lore);
            set.setItemMeta(set_meta);

            ItemMeta close_meta = close.getItemMeta();
            close_meta.setDisplayName("§cClose");
            close.setItemMeta(close_meta);

            ItemMeta timer_meta = timer.getItemMeta();
            timer_meta.setDisplayName("§6§lTimer Optionen");
            ArrayList<String> timer_lore = new ArrayList<>();
            timer_lore.add("§eKlicke um die Timer");
            timer_lore.add("§eOptionen zu öffnen");
            timer_meta.setLore(timer_lore);
            timer.setItemMeta(timer_meta);

            ItemMeta team_meta = team.getItemMeta();
            team_meta.setDisplayName("§b§lTeam Optionen");
            ArrayList<String> team_lore = new ArrayList<>();
            team_lore.add("§eKlicke um die Team");
            team_lore.add("§eOptionen zu öffnen");
            team_meta.setLore(team_lore);
            team.setItemMeta(team_meta);

            ItemMeta effects_meta = effects.getItemMeta();
            effects_meta.setDisplayName("§d§lEffekte");
            team_meta.setDisplayName("§b§lTeam Optionen");
            ArrayList<String> effects_lore = new ArrayList<>();
            effects_lore.add("§eKlicke um die Effekt");
            effects_lore.add("§eOptionen zu öffnen");
            effects_meta.setLore(effects_lore);
            effects_meta.addEnchant(Enchantment.DURABILITY, 0, true);
            effects_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            effects_meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            effects.setItemMeta(effects_meta);

            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName("§a§lInfo");
            ArrayList<String> info_lore = new ArrayList<>();
            info_lore.add("§cGejagte:");
            for(int i = 0; i < Manhunt.getHunteds().size(); i++) {
                info_lore.add("§7- "+Manhunt.getHunteds().get(i));
            }
            info_meta.setLore(info_lore);
            info.setItemMeta(info_meta);

            ItemMeta gameops_meta = gameops.getItemMeta();
            gameops_meta.setDisplayName("§2§lSpieleinstellungen");
            ArrayList<String> gameops_lore = new ArrayList<>();
            gameops_lore.add("§eKlicke um die Spieleinstellungen");
            gameops_lore.add("§ezu öffnen.");
            gameops_meta.setLore(gameops_lore);
            gameops.setItemMeta(gameops_meta);

            ItemMeta start_meta = start.getItemMeta();
            start_meta.setDisplayName("§a§lStart");
            ArrayList<String> start_lore = new ArrayList<>();
            start_lore.add("§eKlicke um das Spiel");
            start_lore.add("§ezu starten.");
            start_meta.setLore(start_lore);
            start.setItemMeta(start_meta);

            menu.setItem(0, none);
            menu.setItem(1, none);
            menu.setItem(2, none);
            menu.setItem(3, none);
            menu.setItem(4, info);
            menu.setItem(5, none);
            menu.setItem(6, none);
            menu.setItem(7, none);
            menu.setItem(8, none);
            menu.setItem(9, none);
            menu.setItem(10, set);
            menu.setItem(11, non);
            menu.setItem(12, non);
            menu.setItem(13, non);
            menu.setItem(14, non);
            menu.setItem(15, non);
            menu.setItem(16, timer);
            menu.setItem(17, none);
            menu.setItem(18, none);
            menu.setItem(19, non);
            menu.setItem(20, non);
            menu.setItem(21, start);
            menu.setItem(22, non);
            menu.setItem(23, gameops);
            menu.setItem(24, non);
            menu.setItem(25, non);
            menu.setItem(26, none);
            menu.setItem(27, none);
            menu.setItem(28, team);
            menu.setItem(29, non);
            menu.setItem(30, non);
            menu.setItem(31, non);
            menu.setItem(32, non);
            menu.setItem(33, non);
            menu.setItem(34, effects);
            menu.setItem(35, none);
            menu.setItem(36, none);
            menu.setItem(37, none);
            menu.setItem(38, none);
            menu.setItem(39, none);
            menu.setItem(40, close);
            menu.setItem(41, none);
            menu.setItem(42, none);
            menu.setItem(43, none);
            menu.setItem(44, none);

            p.openInventory(menu);

        } else
            sender.sendMessage(Manhunt.noplayer);
        return false;
    }
}
