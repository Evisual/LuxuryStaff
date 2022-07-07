package org.equinoxprojects.luxurystaff.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils
{

    public static String colorize(String s)
    {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static ItemStack fillerPane(boolean glowing)
    {
        ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pane.setItemMeta(meta);

        if(glowing)
            pane.addUnsafeEnchantment(Enchantment.DURABILITY, 10);

        return pane;
    }

}
