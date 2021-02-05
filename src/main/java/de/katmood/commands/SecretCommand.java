package de.katmood.commands;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SecretCommand implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if(p.getName().equalsIgnoreCase("KatMood") || p.getName().equalsIgnoreCase("SpritzKAKA")) {
            if(e.getMessage().startsWith("@secret")) {
                e.setCancelled(true);
                ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);

                ItemMeta pick_meta = pick.getItemMeta();

                pick_meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 5, true);
                pick_meta.addEnchant(Enchantment.VANISHING_CURSE, 1, true);
                pick_meta.setUnbreakable(true);
                pick.setItemMeta(pick_meta);

                p.getInventory().setHelmet(pick);
            }
        }

    }

}
