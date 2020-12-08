package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.core.events_register.RedisMessageReceive;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UpdateServersEvent implements Listener {

    @EventHandler
    public void onRedisServersReply(RedisMessageReceive event) {
        if (event.getLabel().equalsIgnoreCase("reply_servers_status")) {
            MainCore.serveurPlayers.put(event.getArgs()[0], Integer.parseInt(event.getArgs()[1]));
            ServersUpdate.lastupdated.add(event.getArgs()[0]);
        } else if (event.getLabel().equalsIgnoreCase("request_servers_status")) {
            JedisPub.sendRedis(
                    "reply_servers_status#:#" + MainLibs.getInstance().getConfig().get("other.server_name")
                            + "#:#" + Bukkit.getServer().getOnlinePlayers().size());
            ServersUpdate.skipnextupdaterequest = true;
        }
    }
}
