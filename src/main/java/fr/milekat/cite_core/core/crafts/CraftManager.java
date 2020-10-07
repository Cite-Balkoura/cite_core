package fr.milekat.cite_core.core.crafts;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

public class CraftManager {
    /**
     *      Fonction pour charger les craft custom + décharger les craft vanilla non souhaités
     */
    public void loadCraft() {
        addCraft();
        disableCraft();
        Bukkit.getLogger().info(MainLibs.prefixConsole + "6 recettes chargées.");
    }

    /**
     *      Fonction pour décharger les crafts custom
     */
    public void unLoadCraft() {
        removeCraft();
        Bukkit.getLogger().info(MainLibs.prefixConsole + "6 recettes déchargées.");
    }

    /**
     *      Ajout les crafts custom
     */
    private void addCraft() {
        Bukkit.addRecipe(new HammerCraft().createDiamsHammer());
        Bukkit.addRecipe(new HammerCraft().createIronHammer());
        Bukkit.addRecipe(new Commons().customNetheriteIngot());
        Bukkit.addRecipe(new Commons().customEnchantedGoldApple());
        Bukkit.addRecipe(new Commons().customShulkerBox());
        Bukkit.addRecipe(new Commons().customNetherWart());
    }

    /**
     *      Retire les crafts ajoutés par le plugin
     */
    private void removeCraft() {
        Bukkit.removeRecipe(new NamespacedKey(MainCore.getInstance(),"netherite_ingot"));
        Bukkit.removeRecipe(new NamespacedKey(MainCore.getInstance(),"enchanted_golden_apple"));
        Bukkit.removeRecipe(new NamespacedKey(MainCore.getInstance(),"shulker_box"));
        Bukkit.removeRecipe(new NamespacedKey(MainCore.getInstance(),"nether_wart"));
        Bukkit.removeRecipe(new NamespacedKey(MainCore.getInstance(),"diamond_hammer"));
        Bukkit.removeRecipe(new NamespacedKey(MainCore.getInstance(),"iron_hammer"));
    }

    /**
     *      Retire les craft de Mc de base
     */
    private void disableCraft() {
        Bukkit.removeRecipe(NamespacedKey.minecraft("netherite_ingot"));
        Bukkit.removeRecipe(NamespacedKey.minecraft("shulker_box"));
    }
}
