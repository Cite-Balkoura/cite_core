package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.core.events_register.RedisMessageReceive;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServersUpdate implements Listener {
    private final ArrayList<String> lastupdated = new ArrayList<>();
    private boolean recentupdate = false;

    public BukkitTask runTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<String, Integer> tempMap = new HashMap<>(MainCore.serveurPlayers);
                for (Map.Entry<String, Integer> loop : tempMap.entrySet()) {
                    if (!lastupdated.contains(loop.getKey())) MainCore.serveurPlayers.remove(loop.getKey());
                }
                lastupdated.clear();
                if (!recentupdate) {
                    JedisPub.sendRedis("update_servers_status#:#" +
                            MainLibs.getInstance().getConfig().get("other.join_server_name"));
                    JedisPub.sendRedis("servers_status_reply#:#" +
                            MainLibs.getInstance().getConfig().get("other.join_server_name")
                            + "#:#" + Bukkit.getServer().getOnlinePlayers().size());
                } else {
                    recentupdate = false;
                }
            }
        }.runTaskTimerAsynchronously(MainCore.getInstance(),0L,300L);
    }

    @EventHandler
    public void onRedisServersReply(RedisMessageReceive event) {
        if (event.getLabel().equalsIgnoreCase("servers_status_reply")) {
            MainCore.serveurPlayers.put(event.getArgs()[0], Integer.parseInt(event.getArgs()[1]));
            lastupdated.add(event.getArgs()[0]);
        } else if (event.getLabel().equalsIgnoreCase("update_servers_status")) {
            JedisPub.sendRedis(
                    "servers_status_reply#:#" + MainLibs.getInstance().getConfig().get("other.join_server_name")
                    + "#:#" + Bukkit.getServer().getOnlinePlayers().size());
            recentupdate = true;
        }
    }
}
