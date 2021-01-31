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

public class GameOptionsGUI implements Listener, CommandExecutor {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String invname = e.getView().getTitle();
        String itemname = e.getCurrentItem().getItemMeta().getDisplayName();

        if(invname.equalsIgnoreCase("§a§lGame Options")) {
            e.setCancelled(true);
            if(e.getCurrentItem().hasItemMeta()) {
                if(itemname.equalsIgnoreCase("§b§lFreeze")) {
                    p.performCommand("freezegui");
                }
            }
        }
    }

    static void renderFreeze(Inventory inv) {
        ItemStack disable = new ItemStack(Material.RED_WOOL);
        ItemStack fünf = new ItemStack(Material.PAPER);
        ItemStack zehn = new ItemStack(Material.PAPER);
        ItemStack fünfzehn = new ItemStack(Material.PAPER);
        ItemStack dreißig = new ItemStack(Material.PAPER);

        ItemMeta disable_meta = disable.getItemMeta();
        disable_meta.setDisplayName("§c§lDeaktivieren");
        ArrayList<String> disable_lore = new ArrayList<>();
        disable_lore.add("§eKlicke um den Freeze am Start");
        disable_lore.add("§eKomplett zu deaktivieren.");
        disable_meta.setLore(disable_lore);
        disable.setItemMeta(disable_meta);

        ItemMeta fünf_meta = fünf.getItemMeta();
        fünf_meta.setDisplayName("§f§l5 Sekunken");
        ArrayList<String> fünf_lore = new ArrayList<>();
        fünf_lore.add("§eSetze die FreezeTime am Start");
        fünf_lore.add("§edes Spiels auf 5 Sekunden.");
        fünf_meta.setLore(fünf_lore);
        fünf.setItemMeta(fünf_meta);

        ItemMeta zehn_meta = zehn.getItemMeta();
        zehn_meta.setDisplayName("§f§l10 Sekunken");
        ArrayList<String> zehn_lore = new ArrayList<>();
        zehn_lore.add("§eSetze die FreezeTime am Start");
        zehn_lore.add("§edes Spiels auf 10 Sekunden.");
        zehn_meta.setLore(zehn_lore);
        zehn.setItemMeta(zehn_meta);

        ItemMeta fünfzehn_meta = fünfzehn.getItemMeta();
        fünfzehn_meta.setDisplayName("§f§l15 Sekunken");
        ArrayList<String> fünfzehn_lore = new ArrayList<>();
        fünfzehn_lore.add("§eSetze die FreezeTime am Start");
        fünfzehn_lore.add("§edes Spiels auf 15 Sekunden.");
        fünfzehn_meta.setLore(fünfzehn_lore);
        fünfzehn.setItemMeta(fünfzehn_meta);

        ItemMeta dreißig_meta = dreißig.getItemMeta();
        dreißig_meta.setDisplayName("§f§l30 Sekunken");
        ArrayList<String> dreißig_lore = new ArrayList<>();
        dreißig_lore.add("§eSetze die FreezeTime am Start");
        dreißig_lore.add("§edes Spiels auf 30 Sekunden.");
        dreißig_meta.setLore(dreißig_lore);
        dreißig.setItemMeta(dreißig_meta);

        Manhunt.setItemNone(inv, 9*6);

        inv.setItem(13, disable);
        inv.setItem(19, fünf);
        inv.setItem(21, zehn);
        inv.setItem(23, fünfzehn);
        inv.setItem(25, dreißig);

    }

    static void renderMain(Inventory inv) {

        ItemStack freeze = new ItemStack(Material.PACKED_ICE);

        ItemMeta freeze_meta = freeze.getItemMeta();
        freeze_meta.setDisplayName("§b§lFreeze");
        ArrayList<String> freeze_lore = new ArrayList<>();
        freeze_lore.add("§eKlicke um den Freeze einzustellen");
        freeze_meta.setLore(freeze_lore);
        freeze.setItemMeta(freeze_meta);

        Manhunt.setItemNone(inv, 9*3);

        inv.setItem(13, freeze);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;

        if(command.getName().equalsIgnoreCase("gameoptionsgui")) {

            Inventory GUI = Bukkit.createInventory(null, 9*3, "§a§lGame Options");
            renderMain(GUI);
            p.openInventory(GUI);
        }
        if(command.getName().equalsIgnoreCase("freezegui")) {

            Inventory Freeze = Bukkit.createInventory(null, 9*6, "§b§lFreeze");
            renderFreeze(Freeze);
            p.openInventory(Freeze);

        }

        return false;
    }
}
