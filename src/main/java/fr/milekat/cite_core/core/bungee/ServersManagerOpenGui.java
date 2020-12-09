package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisServer;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServersManagerOpenGui implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (sender.hasPermission("modo.serveurs.manager")) {
            FastInv gui = new FastInv(36, "§2Online servers");
            gui.setItems(gui.getBorders(), new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name(" ").build(), e -> e.setCancelled(true));
            for (String loop : MainLibs.jedisServers.keySet()) {
                if (!MainCore.serveurPlayers.containsKey(loop)) continue;
                Integer players = MainCore.serveurPlayers.get(loop);
                JedisServer server = MainLibs.jedisServers.get(loop);
                gui.addItem(new ItemBuilder(server.getMaterial()).name(server.getName()).addLore("Population " + players).build(), e -> {
                    e.setCancelled(true);
                    new ServersManagerSendPlayer().sendPlayerToServer(((Player) e.getWhoClicked()),server.getChannel(),null);
                });
            }
            gui.open((Player) sender);
        }
        return true;
    }
}
