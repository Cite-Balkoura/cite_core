package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisSubEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class JoinQuitEvents implements Listener {
    private final HashMap<String, Location> newPlayerLoc = new HashMap<>();
    @EventHandler
    public void onRedisNewPlayerLoc(JedisSubEvent event) {
        if (!event.getLabel().equalsIgnoreCase(MainLibs.getInstance().getConfig().getString("redis.thischannel"))) return;
        if (!event.getArgs()[0].equalsIgnoreCase("set_position")) return;
        if (event.getArgs()[2].equalsIgnoreCase("label") && event.getArgs().length == 4) {
            newPlayerLoc.put(event.getArgs()[1], MainCore.locLabels.getOrDefault(event.getChannel() + "_" + event.getArgs()[3],
                    new Location(Bukkit.getWorld("world"),0,150,0)));
        } else if (event.getArgs().length == 3) {
            String[] coords = event.getArgs()[2].split(";");
            newPlayerLoc.put(event.getArgs()[1], new Location(Bukkit.getWorld("world"),
                    Double.parseDouble(coords[0]),
                    Double.parseDouble(coords[1]),
                    Double.parseDouble(coords[2])));
        } else {
            Bukkit.getLogger().info("Erreur arguments: " + event.getFullMessage());
        }
    }

    @EventHandler
    public void JoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        if (newPlayerLoc.containsKey(event.getPlayer().getName())) {
            event.getPlayer().teleport(newPlayerLoc.get(event.getPlayer().getName()));
            newPlayerLoc.remove(event.getPlayer().getName());
        }
    }

    @EventHandler
    public void QuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }
}
