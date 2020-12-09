package fr.milekat.cite_core.core.crafts;

import fr.milekat.cite_core.MainCore;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class Commons {
    public Recipe customNetheriteIngot() {
        NamespacedKey key = new NamespacedKey(MainCore.getInstance(), "netherite_ingot");
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.NETHERITE_INGOT));
        recipe.shape("DDD", "DGD", "DDD");
        recipe.setIngredient('D', Material.NETHERITE_SCRAP);
        recipe.setIngredient('G', Material.GOLD_BLOCK);
        return recipe;
    }

    public Recipe customEnchantedGoldApple() {
        NamespacedKey key = new NamespacedKey(MainCore.getInstance(), "enchanted_golden_apple");
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.ENCHANTED_GOLDEN_APPLE));
        recipe.shape("BBB", "BAB", "BBB");
        recipe.setIngredient('B', Material.GOLD_BLOCK);
        recipe.setIngredient('A', Material.APPLE);
        return recipe;
    }

    public Recipe customShulkerBox() {
        NamespacedKey key = new NamespacedKey(MainCore.getInstance(), "shulker_box");
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.SHULKER_BOX));
        recipe.shape("SNS", "WEW", "SNS");
        recipe.setIngredient('S', Material.SHULKER_SHELL);
        recipe.setIngredient('W', Material.WITHER_SKELETON_SKULL);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('E', Material.ENDER_CHEST);
        return recipe;
    }
}
