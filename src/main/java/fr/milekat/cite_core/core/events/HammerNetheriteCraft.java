package fr.milekat.cite_core.core.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HammerNetheriteCraft implements Listener {
    @EventHandler
    private void onHamerInventoryClick(InventoryClickEvent event) {
        if (event.getCursor()!=null && event.getCursor().getItemMeta()!=null) {
            ItemStack itemStack = event.getCursor();
            ItemMeta meta = itemStack.getItemMeta();
            if (itemStack.getType().equals(Material.NETHERITE_PICKAXE) &&
                    meta.getDisplayName().contains("Hammer en diams")) {
                meta.setDisplayName("§rHammer en netherite");
                itemStack.setItemMeta(meta);
            }
        }
    }
}
