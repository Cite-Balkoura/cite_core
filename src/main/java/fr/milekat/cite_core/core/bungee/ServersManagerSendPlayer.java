package fr.milekat.cite_core.core.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import org.bukkit.entity.Player;

public class ServersManagerSendPlayer {
    public void sendPlayerToServer(Player player, String server, String loctype) {
        if (loctype!=null) JedisPub.sendRedis(server + "#:#set_position#:#" + player.getName() + "#:#label#:#" + loctype);
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(MainCore.getInstance(), "BungeeCord", out.toByteArray());
    }
}
