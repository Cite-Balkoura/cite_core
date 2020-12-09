package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisSubEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ServersUpdateEvent implements Listener {
    @EventHandler
    public void onRedisServersReply(JedisSubEvent event) {
        if (event.getLabel().equalsIgnoreCase("players_count")) {
            MainCore.serveurPlayers.put(event.getChannel(), Integer.parseInt(event.getArgs()[0]));
            ServersUpdateEngine.updated.put(event.getChannel(), 3);
        }
    }
}
