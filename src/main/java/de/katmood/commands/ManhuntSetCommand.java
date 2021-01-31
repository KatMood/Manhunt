package de.katmood.commands;

import de.katmood.manhunt.Manhunt;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.inventory.meta.SkullMeta;

public class ManhuntSetCommand implements Listener, CommandExecutor {

    static void renderInv(Inventory gui){
        ItemStack none = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
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

        gui.setItem(0, none);
        gui.setItem(1, none);
        gui.setItem(2, none);
        gui.setItem(3, none);
        gui.setItem(4, none);
        gui.setItem(5, none);
        gui.setItem(6, none);
        gui.setItem(7, none);
        gui.setItem(8, none);
        gui.setItem(9, none);
        gui.setItem(10, none);
        gui.setItem(11, none);
        gui.setItem(12, none);
        gui.setItem(13, none);
        gui.setItem(14, none);
        gui.setItem(15, none);
        gui.setItem(16, none);
        gui.setItem(17, none);
        gui.setItem(18, none);
        gui.setItem(19, none);
        gui.setItem(20, none);
        gui.setItem(21, none);
        gui.setItem(22, none);
        gui.setItem(23, none);
        gui.setItem(24, none);
        gui.setItem(25, none);
        gui.setItem(26, none);
        gui.setItem(27, none);
        gui.setItem(28, none);
        gui.setItem(29, none);
        gui.setItem(30, none);
        gui.setItem(31, none);
        gui.setItem(32, none);
        gui.setItem(33, none);
        gui.setItem(34, none);
        gui.setItem(35, none);
        gui.setItem(36, none);
        gui.setItem(37, none);
        gui.setItem(38, none);
        gui.setItem(39, none);
        gui.setItem(40, none);
        gui.setItem(41, none);
        gui.setItem(42, none);
        gui.setItem(43, none);
        gui.setItem(44, none);
        gui.setItem(45, none);
        gui.setItem(46, none);
        gui.setItem(47, none);
        gui.setItem(48, back);
        gui.setItem(49, close);
        gui.setItem(50, none);
        gui.setItem(51, none);
        gui.setItem(52, none);
        gui.setItem(53, none);

        int i = 0;
        for(Player cp : Bukkit.getOnlinePlayers()){
            String name = cp.getName();
            if(Manhunt.Hunted.get(name)){
                gui.setItem(i, getPlayerHeadByName(name, ChatColor.GREEN));
            } else {
                gui.setItem(i, getPlayerHeadByName(name, ChatColor.RED));
            }
            i++;
        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getView().getTitle().equalsIgnoreCase("§c§lGejagte auswählen")){
            e.setCancelled(true);
            if(e.getCurrentItem()!=null){
                if(e.getCurrentItem().hasItemMeta()){
                    if(e.getCurrentItem().getType() == Material.PLAYER_HEAD){
                        if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§a")){
                            Manhunt.Hunted.put(e.getCurrentItem().getItemMeta().getDisplayName().replace("§a", ""), false);
                        }
                        if(e.getCurrentItem().getItemMeta().getDisplayName().startsWith("§c")){
                            Manhunt.Hunted.put(e.getCurrentItem().getItemMeta().getDisplayName().replace("§c", ""), true);
                        }
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
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(command.getName().equalsIgnoreCase("manhuntset")){
                if(args.length<2){
                    p.sendMessage(Manhunt.prefix+"§c/manhuntset Spieler1 Spieler2");
                }
                if(args.length>2){
                    p.sendMessage(Manhunt.prefix+"§c/manhuntset Spieler1 Spieler2");
                }
                if(args.length==2){
                    if(args[0] == args[1]){
                        p.sendMessage(Manhunt.prefix+"§cDu musst zwei unterschiedliche Spieler auswählen!");
                    } else {
                        for(String cp : Manhunt.Hunted.keySet()){
                            Manhunt.Hunted.put(cp,  false);
                        }
                        Manhunt.Hunted.put(args[0], true);
                        Manhunt.Hunted.put(args[1], true);
                        Manhunt.savePlayerData();
                        p.sendMessage(Manhunt.prefix+"§aDie gejagten Spieler sind nun §c"+args[0]+" §aund §c"+args[1]);
                    }
                }
            }
            if(command.getName().equalsIgnoreCase("manhuntsetgui")){
                Inventory gui = Bukkit.createInventory(null, 9*6, "§c§lGejagte auswählen");
                renderInv(gui);
                p.openInventory(gui);

            }
            if(command.getName().equalsIgnoreCase("manhuntdelete")){
                if(args.length==1){
                    Manhunt.Hunted.remove(args[0]);
                } else
                    p.sendMessage("Du hast einfach voll verkackt.");
            }
        } else
            sender.sendMessage(Manhunt.noplayer);
        return false;
    }
    public static ItemStack getPlayerHeadByName(String playerName, ChatColor color) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName));
        meta.setDisplayName(color + playerName);
        skull.setItemMeta(meta);

        return skull;
    }
}
