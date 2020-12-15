package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisSubEvent;
import fr.milekat.cite_libs.utils_tools.LocationParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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
            //  Check if player has join the server
            Player player = Bukkit.getPlayerExact(event.getArgs()[1]);
            if (player==null) {
                //  if not store his new location for when he join
                newPlayerLoc.put(event.getArgs()[1], MainCore.locLabels.getOrDefault(
                        MainLibs.getInstance().getConfig().getString("redis.thischannel") + "_boat",
                        MainCore.defaultLocation));
            } else {
                player.teleport(LocationParser.getLocation(event.getArgs()[3], event.getArgs()[4]));
            }
        } else if (event.getArgs()[2].equalsIgnoreCase("loc") && event.getArgs().length == 5) {
            //  Check if player has join the server
            Player player = Bukkit.getPlayerExact(event.getArgs()[1]);
            if (player==null) {
                //  if not store his new location for when he join
                newPlayerLoc.put(event.getArgs()[1], LocationParser.getLocation(event.getArgs()[3], event.getArgs()[4]));
            } else {
                player.teleport(LocationParser.getLocation(event.getArgs()[3], event.getArgs()[4]));
            }
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
