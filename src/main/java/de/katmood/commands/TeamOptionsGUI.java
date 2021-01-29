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

public class TeamOptionsGUI implements Listener, CommandExecutor {

    static void renderInv(Inventory gui){

        ItemStack none = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemStack back = new ItemStack(Material.ARROW);
        ItemStack tpe = new ItemStack(Material.ENDER_PEARL);
        ItemStack tpd = new ItemStack(Material.ENDER_PEARL);
        ItemStack inve = new ItemStack(Material.BARREL);
        ItemStack invd = new ItemStack(Material.BARREL);
        ItemStack chate = new ItemStack(Material.OAK_SIGN);
        ItemStack chatd = new ItemStack(Material.OAK_SIGN);

        ItemMeta none_meta = none.getItemMeta();
        none_meta.setDisplayName(" ");
        none.setItemMeta(none_meta);

        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName("§cClose");
        close.setItemMeta(close_meta);

        ItemMeta back_meta = back.getItemMeta();
        back_meta.setDisplayName("§fBack");
        back.setItemMeta(back_meta);

        ItemMeta tpe_meta = tpe.getItemMeta();
        tpe_meta.setDisplayName("§3§lTeamTeleport §7§l¦ §c§lINAKTIV");
        ArrayList<String> tpe_lore = new ArrayList<>();
        tpe_lore.add("§eTeamTeleport ist inaktiv.");
        tpe_lore.add("§eKlicke um TeamTeleport zu.");
        tpe_lore.add("§eaktivieren.");
        tpe_meta.setLore(tpe_lore);
        tpe.setItemMeta(tpe_meta);

        ItemMeta tpd_meta = tpd.getItemMeta();
        tpd_meta.setDisplayName("§3§lTeamTeleport §7§l¦ §a§lAKTIV");
        ArrayList<String> tpd_lore = new ArrayList<>();
        tpd_lore.add("§eTeamTeleport ist aktiv.");
        tpd_lore.add("§eKlicke um TeamTeleport zu");
        tpd_lore.add("§edeaktivieren.");
        tpd_meta.setLore(tpd_lore);
        tpd_meta.addEnchant(Enchantment.DURABILITY, 0, true);
        tpd_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        tpd.setItemMeta(tpd_meta);

        ItemMeta inve_meta = inve.getItemMeta();
        inve_meta.setDisplayName("§2§lTeamInventar §7§l¦ §c§lINAKTIV");
        ArrayList<String> inve_lore = new ArrayList<>();
        inve_lore.add("§eTeamInventar ist inaktiv.");
        inve_lore.add("§eKlicke um TeamInventar zu");
        inve_lore.add("§eaktivieren.");
        inve_meta.setLore(inve_lore);
        inve.setItemMeta(inve_meta);

        ItemMeta invd_meta = invd.getItemMeta();
        invd_meta.setDisplayName("§2§lTeamInventar §7§l¦ §a§lAKTIV");
        ArrayList<String> invd_lore = new ArrayList<>();
        invd_lore.add("§eTeamInventar ist aktiv.");
        invd_lore.add("§eKlicke um TeamInventar zu");
        invd_lore.add("§edeaktivieren");
        invd_meta.setLore(invd_lore);
        invd_meta.addEnchant(Enchantment.DURABILITY, 0, true);
        invd_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        invd.setItemMeta(invd_meta);

        ItemMeta chate_meta = chate.getItemMeta();
        chate_meta.setDisplayName("§6§lTeamChat §7§l¦ §c§lINAKTIV");
        ArrayList<String> chate_lore = new ArrayList<>();
        chate_lore.add("§eTeamChat ist inaktiv.");
        chate_lore.add("§eKlicke um TeamChat zu");
        chate_lore.add("§eaktivieren.");
        chate_meta.setLore(chate_lore);
        chate.setItemMeta(chate_meta);

        ItemMeta chatd_meta = chatd.getItemMeta();
        chatd_meta.setDisplayName("§6§lTeamChat §7§l¦ §a§lAKTIV");
        ArrayList<String> chatd_lore = new ArrayList<>();
        chatd_lore.add("§eTeamChat ist aktiv.");
        chatd_lore.add("§eKlicke um es zu");
        chatd_lore.add("§edeaktivieren");
        chatd_meta.setLore(chatd_lore);
        chatd_meta.addEnchant(Enchantment.DURABILITY, 0, true);
        chatd_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        chatd.setItemMeta(chatd_meta);

        for(int i = 0; i < 27; i++){
            gui.setItem(i, none);
        }
        gui.setItem(21, back);
        gui.setItem(22, close);
        if(Manhunt.ttp)
            gui.setItem(10, tpd);
        else
            gui.setItem(10, tpe);
        if(Manhunt.tinv)
            gui.setItem(13, invd);
        else
            gui.setItem(13, inve);
        if(Manhunt.tchat)
            gui.setItem(16, chatd);
        else
            gui.setItem(16, chate);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equalsIgnoreCase("§b§lTeam Options")){
            e.setCancelled(true);
            if(e.getCurrentItem().hasItemMeta()){
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lTeamTeleport §7§l¦ §c§lINAKTIV")){
                    Manhunt.ttp = true;
                    Manhunt.saveTeamConfig();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lTeamTeleport §7§l¦ §a§lAKTIV")){
                    Manhunt.ttp = false;
                    Manhunt.saveTeamConfig();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2§lTeamInventar §7§l¦ §c§lINAKTIV")){
                    Manhunt.tinv = true;
                    Manhunt.saveTeamConfig();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2§lTeamInventar §7§l¦ §a§lAKTIV")){
                    Manhunt.tinv = false;
                    Manhunt.saveTeamConfig();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lTeamChat §7§l¦ §c§lINAKTIV")){
                    Manhunt.tchat = true;
                    Manhunt.saveTeamConfig();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lTeamChat §7§l¦ §a§lAKTIV")){
                    Manhunt.tchat = false;
                    Manhunt.saveTeamConfig();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")){
                    p.closeInventory();
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§fBack")){
                    p.closeInventory();
                    p.performCommand("menu");
                }
                renderInv(e.getInventory());
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;

        Inventory GUI = Bukkit.createInventory(null, 9*3, "§b§lTeam Options");

        p.openInventory(GUI);
        renderInv(GUI);

    return false;
    }
}
