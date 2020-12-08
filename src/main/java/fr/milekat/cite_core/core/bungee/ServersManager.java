package fr.milekat.cite_core.core.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

import java.util.Map;

public class ServersManager {

    public void modoServersGui(Player player) {
        FastInv gui = new FastInv(InventoryType.CHEST, ChatColor.DARK_AQUA + "Servers Manager !");
        gui.setItems(gui.getBorders(), new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).name("").build());
        for (Map.Entry<Integer, String> loop : MainCore.serveurPlayers.entrySet()) {
            gui.addItem(new ItemBuilder(Material.GRASS_BLOCK).name(loop.getValue()).name("").addLore("Population " + loop.getKey())
                    .build(), e -> {
                sendPlayerToServer(((Player) e.getWhoClicked()),loop.getValue(),null);
            });
            gui.open(player);
        }
    }

    public void sendPlayerToServer(Player player, String server, String loctype) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(MainCore.getInstance(), "BungeeCord", out.toByteArray());
        if (loctype!=null) JedisPub.sendRedis(server + "#:#set_position#:#" + player.getName() + "#:#" + loctype);
    }
}
