package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class ServersUpdateEngine {
    //  Si dans cette liste = update depuis le dernier recensement !
    public static final HashMap<String, Integer> updated = new HashMap<>();

    public BukkitTask runTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                for (String server : MainLibs.jedisServers.keySet()) {
                    if (!updated.containsKey(server)) {
                        MainCore.serveurPlayers.remove(server);
                    } else {
                        updated.put(server, updated.get(server) - 1);
                        if (updated.get(server) < 1) updated.remove(server);
                    }
                }
                JedisPub.sendRedis("players_count#:#" + Bukkit.getServer().getOnlinePlayers().size());
            }
        }.runTaskTimerAsynchronously(MainCore.getInstance(),0L,200L);
    }


}
