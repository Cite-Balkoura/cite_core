package fr.milekat.cite_core.core.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import org.bukkit.entity.Player;

public class ServersManager {
    public void sendPlayerToServer(Player player, String server, String loctype) {
        if (loctype!=null) JedisPub.sendRedis(server + "#:#set_position#:#" + player.getName() + "#:#" + loctype);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(MainLibs.getInstance().getConfig().getString("other.servers_list." + server));
        player.sendPluginMessage(MainCore.getInstance(), "BungeeCord", out.toByteArray());
    }
}
