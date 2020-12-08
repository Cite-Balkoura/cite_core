package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServersUpdate {
    public static final ArrayList<String> lastupdated = new ArrayList<>();
    public static boolean skipnextupdaterequest = false;

    public BukkitTask runTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                HashMap<String, Integer> tempMap = new HashMap<>(MainCore.serveurPlayers);
                for (Map.Entry<String, Integer> loop : tempMap.entrySet()) {
                    if (!lastupdated.contains(loop.getKey())) MainCore.serveurPlayers.remove(loop.getKey());
                }
                lastupdated.clear();
                if (!skipnextupdaterequest) {
                    JedisPub.sendRedis("request_servers_status#:#" +
                            MainLibs.getInstance().getConfig().get("other.server_name"));
                    JedisPub.sendRedis("reply_servers_status#:#" +
                            MainLibs.getInstance().getConfig().get("other.server_name")
                            + "#:#" + Bukkit.getServer().getOnlinePlayers().size());
                } else {
                    skipnextupdaterequest = false;
                }
            }
        }.runTaskTimerAsynchronously(MainCore.getInstance(),0L,300L);
    }


}
