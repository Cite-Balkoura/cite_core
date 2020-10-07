package fr.milekat.cite_core.core.crafts;

import fr.milekat.cite_core.MainCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class HammerCraft {
    public Recipe createDiamsHammer() {
        ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        if (meta!=null) {
            meta.setDisplayName("§rHammer en diams");
            meta.setCustomModelData(1);
            item.setItemMeta(meta);
        }
        NamespacedKey key = new NamespacedKey(MainCore.getInstance(), "diamond_hammer");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("BBB", "BBB", " S ");
        recipe.setIngredient('B', Material.DIAMOND_BLOCK);
        recipe.setIngredient('S', Material.STICK);
        return recipe;
    }

    public Recipe createIronHammer() {
        ItemStack item = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        if (meta!=null) {
            meta.setDisplayName("§rHammer en fer");
            meta.setCustomModelData(1);
            item.setItemMeta(meta);
        }
        NamespacedKey key = new NamespacedKey(MainCore.getInstance(), "iron_hammer");
        ShapedRecipe recipe = new ShapedRecipe(key, item);
        recipe.shape("BBB", "BBB", " S ");
        recipe.setIngredient('B', Material.IRON_BLOCK);
        recipe.setIngredient('S', Material.STICK);
        return recipe;
    }
}
