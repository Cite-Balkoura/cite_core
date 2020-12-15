package fr.milekat.cite_core.core.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import fr.milekat.cite_libs.utils_tools.LocationParser;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ServersManagerSendPlayer {
    /**
     *      Send player to server
     */
    public void sendPlayerToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(MainCore.getInstance(), "BungeeCord", out.toByteArray());
    }

    /**
     *      Send player to server at location label
     */
    public void sendPlayerToServerWithLabel(Player player, String server, String pos) {
        JedisPub.sendRedis(server + "#:#set_position#:#" + player.getName() + "#:#label#:#" + pos);
        sendPlayerToServer(player, server);
    }

    /**
     *      Send player to server at location xyz
     */
    public void sendPlayerToServerWithLoc(Player player, String server, Location loc) {
        JedisPub.sendRedis(server + "#:#set_position#:#" + player.getName() + "#:#loc#:#" + LocationParser.getString(loc));
        sendPlayerToServer(player, server);
    }
}
