package fr.milekat.cite_core.core.events;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

/**
 *      From https://github.com/NikV2/CombatPlus/blob/master/src/main/java/me/nik/combatplus/utils/SetAttackSpeed.java
 *      From https://github.com/NikV2/CombatPlus/blob/master/src/main/java/me/nik/combatplus/listeners/DamageModifiers.java
 *      Damage copy from BedRock Edition
 */
public class DamageModifiers implements Listener {

    /**
     *      Set de l'atttack speed 1.8
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        AttributeInstance pAttributes = event.getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (pAttributes!=null) pAttributes.setBaseValue(24);
        event.getPlayer().saveData();
    }

    /**
     *      This Listener Changes the Damage Dealt to All Entities to the Old Values
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        ItemStack tool = player.getInventory().getItemInMainHand();
        String weapon = tool.getType().name();
        switch (weapon) {
            //Swords
            case "NETHERITE_SWORD":
                damageConverter(event, tool, 1);
                break;
            case "DIAMOND_SWORD":
                damageConverter(event, tool, 1);
                break;
            case "GOLDEN_SWORD":
            case "GOLD_SWORD":
                damageConverter(event, tool, 1);
                break;
            case "IRON_SWORD":
                damageConverter(event, tool, 1);
                break;
            case "STONE_SWORD":
                damageConverter(event, tool, 1);
                break;
            case "WOODEN_SWORD":
                damageConverter(event, tool, 1);
                break;
            //Pickaxes
            case "NETHERITE_PICKAXE":
                damageConverter(event, tool, 0);
                break;
            case "DIAMOND_PICKAXE":
                damageConverter(event, tool, 0);
                break;
            case "GOLDEN_PICKAXE":
            case "GOLD_PICKAXE":
                damageConverter(event, tool, 0);
                break;
            case "IRON_PICKAXE":
                damageConverter(event, tool, 0);
                break;
            case "STONE_PICKAXE":
                damageConverter(event, tool, 0);
                break;
            case "WOODEN_PICKAXE":
                damageConverter(event, tool, 0);
                break;
            //Axes
            case "NETHERITE_AXE":
                damageConverter(event, tool, -2);
                break;
            case "DIAMOND_AXE":
                damageConverter(event, tool, -2);
                break;
            case "GOLDEN_AXE":
            case "GOLD_AXE":
                damageConverter(event, tool, -3);
                break;
            case "IRON_AXE":
                damageConverter(event, tool, -3);
                break;
            case "STONE_AXE":
                damageConverter(event, tool, -4);
                break;
            case "WOODEN_AXE":
                damageConverter(event, tool, -3);
                break;
            //Hoes (Again, the tool!)
            case "NETHERITE_HOE":
                damageConverter(event, tool, 5);
                break;
            case "DIAMOND_HOE":
                damageConverter(event, tool, 4);
                break;
            case "GOLDEN_HOE":
            case "GOLD_HOE":
                damageConverter(event, tool, 1);
                break;
            case "IRON_HOE":
                damageConverter(event, tool, 3);
                break;
            case "STONE_HOE":
                damageConverter(event, tool, 2);
                break;
            case "WOODEN_HOE":
                damageConverter(event, tool, 1);
                break;
            //Shovels
            case "NETHERITE_SHOVEL":
                damageConverter(event, tool, -0.5);
                break;
            case "DIAMOND_SHOVEL":
            case "DIAMOND_SPADE":
                damageConverter(event, tool, -0.5);
                break;
            case "GOLDEN_SHOVEL":
            case "GOLDEN_SPADE":
            case "GOLD_SHOVEL":
            case "GOLD_SPADE":
                damageConverter(event, tool, -0.5);
                break;
            case "IRON_SHOVEL":
            case "IRON_SPADE":
                damageConverter(event, tool, -0.5);
                break;
            case "STONE_SHOVEL":
            case "STONE_SPADE":
                damageConverter(event, tool, -0.5);
                break;
            case "WOODEN_SHOVEL":
            case "WOODEN_SPADE":
                damageConverter(event, tool, -0.5);
                break;
        }
    }

    /**
     *      Ajout des dégats 'modifier' + du Sharpness 1.8 (newSharpDmg)
     */
    private void damageConverter(EntityDamageByEntityEvent event, ItemStack item, double modifier) {
        double newDmg = event.getDamage() + modifier;
        if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
            double sharpLvl = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            double oldSharpDmg = sharpLvl >= 1 ? sharpLvl * 1.25 : 0; //1.8
            double newSharpDmg = sharpLvl >= 1 ? 1 + (sharpLvl - 1) * 0.5 : 0; //1.9+
            newDmg = newDmg + oldSharpDmg - newSharpDmg;
        }
        event.setDamage(newDmg);
    }
}
