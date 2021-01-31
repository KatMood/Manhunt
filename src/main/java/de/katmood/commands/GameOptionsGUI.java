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
import sun.jvm.hotspot.ui.ObjectHistogramPanel;

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
                    p.sendMessage("Freeze");
                }
            }
        }
    }

    static void renderMain(Inventory inv) {

        ItemStack none = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack freeze = new ItemStack(Material.PACKED_ICE);

        ItemMeta none_meta = none.getItemMeta();
        none_meta.setDisplayName(" ");
        none.setItemMeta(none_meta);

        ItemMeta freeze_meta = freeze.getItemMeta();
        freeze_meta.setDisplayName("§b§lFreeze");
        ArrayList<String> freeze_lore = new ArrayList<>();
        freeze_lore.add("§eKlicke um den Freeze einzustellen");
        freeze_meta.setLore(freeze_lore);
        freeze.setItemMeta(freeze_meta);

        for(int i = 0; i < 9*3; i++) {
            inv.setItem(i, none);
        }

        inv.setItem(13, freeze);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))
            sender.sendMessage(Manhunt.noplayer);

        Player p = (Player) sender;

        Inventory GUI = Bukkit.createInventory(null, 9*3, "§a§lGame Options");

        renderMain(GUI);

        p.openInventory(GUI);

        return false;
    }
}
