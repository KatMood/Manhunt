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
import java.util.Objects;

public class GameOptionsGUI implements Listener, CommandExecutor {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String invname = e.getView().getTitle();
        String itemname = Objects.requireNonNull(e.getCurrentItem()).getItemMeta().getDisplayName();

        if(invname.equalsIgnoreCase("§a§lGame Options")) {
            e.setCancelled(true);
            if(e.getCurrentItem().hasItemMeta()) {
                if(itemname.equalsIgnoreCase("§b§lFreeze")) {
                    p.closeInventory();
                    p.performCommand("freezegui");
                }
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§c§lTod")) {
                    p.closeInventory();
                    p.performCommand("deathoptionsgui");
                }
            }
        }
        if(invname.equalsIgnoreCase("§b§lFreeze")) {
            e.setCancelled(true);
                if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE)
                    return;
                if(itemname.equalsIgnoreCase("§c§lDeaktivieren")); {
                    Manhunt.freeze = false;
                    Manhunt.freezeTime = 0;
                    Manhunt.saveGameData();
                    if(Manhunt.getMods().contains(p.getName())) {

                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                if(itemname.equalsIgnoreCase("§f§l5 Sekunken")) {
                    if(Manhunt.getMods().contains(p.getName())) {
                        Manhunt.freeze = true;
                        Manhunt.freezeTime = 5;
                        Manhunt.saveGameData();
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                if(itemname.equalsIgnoreCase("§f§l10 Sekunken")) {
                    if(Manhunt.getMods().contains(p.getName())) {
                        Manhunt.freeze = true;
                        Manhunt.freezeTime = 10;
                        Manhunt.saveGameData();
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                if(itemname.equalsIgnoreCase("§f§l15 Sekunken")) {
                    if(Manhunt.getMods().contains(p.getName())) {
                        Manhunt.freeze = true;
                        Manhunt.freezeTime = 15;
                        Manhunt.saveGameData();
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                if(itemname.equalsIgnoreCase("§f§l30 Sekunken")) {
                    if(Manhunt.getMods().contains(p.getName())) {
                        Manhunt.freeze = true;
                        Manhunt.freezeTime = 30;
                        Manhunt.saveGameData();
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                if(itemname.equalsIgnoreCase("§f§l1 Minute")) {
                    if(Manhunt.getMods().contains(p.getName())) {
                        Manhunt.freeze = true;
                        Manhunt.freezeTime = 60;
                        Manhunt.saveGameData();
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                if(itemname.equalsIgnoreCase("§f§l2 Minuten")) {
                    if(Manhunt.getMods().contains(p.getName())) {
                        Manhunt.freeze = true;
                        Manhunt.freezeTime = 120;
                        Manhunt.saveGameData();
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                if(itemname.equalsIgnoreCase("§f§l3 Minuten")) {
                    if(Manhunt.getMods().contains(p.getName())) {
                        Manhunt.freeze = true;
                        Manhunt.freezeTime = 180;
                        Manhunt.saveGameData();
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                if(itemname.equalsIgnoreCase("§f§l5 Minuten")) {
                    if(Manhunt.getMods().contains(p.getName())) {
                        Manhunt.freeze = true;
                        Manhunt.freezeTime = 300;
                        Manhunt.saveGameData();
                    } else {
                        p.sendMessage(Manhunt.prefix+"§cDu musst Moderator sein um Einstellungen zu ändern!");
                    }
                }
                renderFreeze(e.getInventory());
        }
    }

    static void renderFreeze(Inventory inv) {
        ItemStack disable = new ItemStack(Material.RED_WOOL);
        ItemStack fünf = new ItemStack(Material.PAPER);
        ItemStack zehn = new ItemStack(Material.PAPER);
        ItemStack fünfzehn = new ItemStack(Material.PAPER);
        ItemStack dreißig = new ItemStack(Material.PAPER);
        ItemStack einsmin = new ItemStack(Material.PAPER);
        ItemStack zweimin = new ItemStack(Material.PAPER);
        ItemStack dreimin = new ItemStack(Material.PAPER);
        ItemStack fünfmin = new ItemStack(Material.PAPER);

        ItemMeta disable_meta = disable.getItemMeta();
        disable_meta.setDisplayName("§c§lDeaktivieren");
        ArrayList<String> disable_lore = new ArrayList<>();
        disable_lore.add("§eKlicke um den Freeze am Start");
        disable_lore.add("§eKomplett zu deaktivieren.");
        disable_meta.setLore(disable_lore);
        if(!Manhunt.freeze) {
            disable_meta.addEnchant(Enchantment.DURABILITY, 0, true);
            disable_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        disable.setItemMeta(disable_meta);

        ItemMeta fünf_meta = fünf.getItemMeta();
        fünf_meta.setDisplayName("§f§l5 Sekunken");
        ArrayList<String> fünf_lore = new ArrayList<>();
        fünf_lore.add("§eSetze die FreezeTime am Start");
        fünf_lore.add("§edes Spiels auf 5 Sekunden.");
        fünf_meta.setLore(fünf_lore);
        if(Manhunt.freeze) {
            if(Manhunt.freezeTime == 5) {
                fünf_meta.addEnchant(Enchantment.DURABILITY, 0, true);
                fünf_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        fünf.setItemMeta(fünf_meta);

        ItemMeta zehn_meta = zehn.getItemMeta();
        zehn_meta.setDisplayName("§f§l10 Sekunken");
        ArrayList<String> zehn_lore = new ArrayList<>();
        zehn_lore.add("§eSetze die FreezeTime am Start");
        zehn_lore.add("§edes Spiels auf 10 Sekunden.");
        zehn_meta.setLore(zehn_lore);
        if(Manhunt.freeze) {
            if(Manhunt.freezeTime == 10) {
                zehn_meta.addEnchant(Enchantment.DURABILITY, 0, true);
                zehn_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        zehn.setItemMeta(zehn_meta);

        ItemMeta fünfzehn_meta = fünfzehn.getItemMeta();
        fünfzehn_meta.setDisplayName("§f§l15 Sekunken");
        ArrayList<String> fünfzehn_lore = new ArrayList<>();
        fünfzehn_lore.add("§eSetze die FreezeTime am Start");
        fünfzehn_lore.add("§edes Spiels auf 15 Sekunden.");
        fünfzehn_meta.setLore(fünfzehn_lore);
        if(Manhunt.freeze) {
            if(Manhunt.freezeTime == 15) {
                fünfzehn_meta.addEnchant(Enchantment.DURABILITY, 0, true);
                fünfzehn_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        fünfzehn.setItemMeta(fünfzehn_meta);

        ItemMeta dreißig_meta = dreißig.getItemMeta();
        dreißig_meta.setDisplayName("§f§l30 Sekunken");
        ArrayList<String> dreißig_lore = new ArrayList<>();
        dreißig_lore.add("§eSetze die FreezeTime am Start");
        dreißig_lore.add("§edes Spiels auf 30 Sekunden.");
        dreißig_meta.setLore(dreißig_lore);
        if(Manhunt.freeze) {
            if(Manhunt.freezeTime == 30) {
                dreißig_meta.addEnchant(Enchantment.DURABILITY, 0, true);
                dreißig_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        dreißig.setItemMeta(dreißig_meta);

        ItemMeta einsmin_meta = einsmin.getItemMeta();
        einsmin_meta.setDisplayName("§f§l1 Minute");
        ArrayList<String> einsmin_lore = new ArrayList<>();
        einsmin_lore.add("§eSetze die FreezeTime am Start");
        einsmin_lore.add("§edes Spiels auf 1 Minute.");
        einsmin_meta.setLore(dreißig_lore);
        if(Manhunt.freeze) {
            if(Manhunt.freezeTime == 60) {
                einsmin_meta.addEnchant(Enchantment.DURABILITY, 0, true);
                einsmin_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        einsmin.setItemMeta(einsmin_meta);

        ItemMeta zweimin_meta = zweimin.getItemMeta();
        zweimin_meta.setDisplayName("§f§l2 Minuten");
        ArrayList<String> zweimin_lore = new ArrayList<>();
        zweimin_lore.add("§eSetze die FreezeTime am Start");
        zweimin_lore.add("§edes Spiels auf 2 Minuten.");
        zweimin_meta.setLore(dreißig_lore);
        if(Manhunt.freeze) {
            if(Manhunt.freezeTime == 120) {
                zweimin_meta.addEnchant(Enchantment.DURABILITY, 0, true);
                zweimin_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        zweimin.setItemMeta(zweimin_meta);

        ItemMeta dreimin_meta = dreimin.getItemMeta();
        dreimin_meta.setDisplayName("§f§l3 Minuten");
        ArrayList<String> dreimin_lore = new ArrayList<>();
        dreimin_lore.add("§eSetze die FreezeTime am Start");
        dreimin_lore.add("§edes Spiels auf 3 Minuten.");
        dreimin_meta.setLore(dreißig_lore);
        if(Manhunt.freeze) {
            if(Manhunt.freezeTime == 180) {
                dreimin_meta.addEnchant(Enchantment.DURABILITY, 0, true);
                dreimin_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        dreimin.setItemMeta(dreimin_meta);

        ItemMeta fünfmin_meta = fünfmin.getItemMeta();
        fünfmin_meta.setDisplayName("§f§l5 Minuten");
        ArrayList<String> fünfmin_lore = new ArrayList<>();
        fünfmin_lore.add("§eSetze die FreezeTime am Start");
        fünfmin_lore.add("§edes Spiels auf 5 Minuten.");
        fünfmin_meta.setLore(dreißig_lore);
        if(Manhunt.freeze) {
            if(Manhunt.freezeTime == 300) {
                fünfmin_meta.addEnchant(Enchantment.DURABILITY, 0, true);
                fünfmin_meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
        }
        fünfmin.setItemMeta(fünfmin_meta);

        Manhunt.setItemNone(inv, 9*6);

        inv.setItem(13, disable);
        inv.setItem(19, fünf);
        inv.setItem(21, zehn);
        inv.setItem(23, fünfzehn);
        inv.setItem(25, dreißig);
        inv.setItem(37, einsmin);
        inv.setItem(39, zweimin);
        inv.setItem(41, dreimin);
        inv.setItem(43, fünfmin);

    }

    static void renderMain(Inventory inv) {

        ItemStack freeze = new ItemStack(Material.PACKED_ICE);
        ItemStack death = new ItemStack(Material.ZOMBIE_HEAD);

        ItemMeta freeze_meta = freeze.getItemMeta();
        freeze_meta.setDisplayName("§b§lFreeze");
        ArrayList<String> freeze_lore = new ArrayList<>();
        freeze_lore.add("§eKlicke um den Freeze einzustellen");
        freeze_meta.setLore(freeze_lore);
        freeze.setItemMeta(freeze_meta);

        ItemMeta death_meta = death.getItemMeta();
        death_meta.setDisplayName("§c§lTod");
        ArrayList<String> death_lore = new ArrayList<>();
        death_lore.add("§eKlicke um Einstellungen über");
        death_lore.add("§eden Tod der Gejagten einzustellen");
        death_meta.setLore(death_lore);
        death.setItemMeta(death_meta);

        Manhunt.setItemNone(inv, 9*3);

        inv.setItem(12, freeze);
        inv.setItem(14, death);

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
