package fr.milekat.cite_core.event_game;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Event_Init {
    public final ItemStack eggBoxEmpty = new ItemStack(Material.SHULKER_SPAWN_EGG,1);
    public final String boxPlayer = "[BoxEvent] Clique & récupère !";
    public final String boxModo = "[BoxEvent] de ";

    public Event_Init(){
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Placer la shullker box");
        eggBoxEmpty.addUnsafeEnchantment(Enchantment.MENDING,1);
        ItemMeta box = eggBoxEmpty.getItemMeta();
        box.setLore(lore);
        box.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        eggBoxEmpty.setItemMeta(box);
    }
}
