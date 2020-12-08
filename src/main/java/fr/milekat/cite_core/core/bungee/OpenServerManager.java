package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.Map;

public class OpenServerManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (sender.hasPermission("modo.serveurs.manager")) {
            FastInv gui = new FastInv(InventoryType.CHEST, ChatColor.DARK_AQUA + "Servers");
            gui.setItems(gui.getBorders(), new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build(), e -> e.setCancelled(true));
            for (Map.Entry<String, Integer> loop : MainCore.serveurPlayers.entrySet()) {
                gui.addItem(new ItemBuilder(Material.GRASS_BLOCK).name(loop.getKey()).addLore("Population " + loop.getValue()).build(), e -> {
                    e.setCancelled(true);
                    new ServersManager().sendPlayerToServer(((Player) e.getWhoClicked()),loop.getKey(),null);
                });
            }
            gui.open((Player) sender);
        }
        return true;
    }
}
