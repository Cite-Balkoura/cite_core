package fr.milekat.cite_core.core.utils;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_core.core.bungee.BungeeSendPlayer;
import fr.milekat.cite_core.core.bungee.JoinQuitEvents;
import fr.milekat.cite_core.core.bungee.ServersManagerOpenGui;
import fr.milekat.cite_core.core.bungee.ServersUpdateEvent;
import fr.milekat.cite_core.core.commands.*;
import fr.milekat.cite_core.core.events.DamageModifiers;
import fr.milekat.cite_core.core.events.HammerNetheriteCraft;
import fr.milekat.cite_core.core.topluck.openGui;
import fr.milekat.cite_core.event_game.Event_Cmd;
import fr.milekat.cite_core.event_game.Event_Event;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.LocationParser;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PluginLoader {
    /**
     *      Chargement des évents
     */
    public void loadEvents(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(new JoinQuitEvents(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new Event_Event(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BungeeSendPlayer(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new HammerNetheriteCraft(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new DamageModifiers(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ServersUpdateEvent(), plugin);
    }

    /**
     *      Chargement des commandes
     */
    public void loadCommands(JavaPlugin plugin) {
        plugin.getCommand("topluck").setExecutor(new openGui());
        plugin.getCommand("event").setExecutor(new Event_Cmd());
        plugin.getCommand("sign").setExecutor(new SignEdit());
        plugin.getCommand("web").setExecutor(new WebLinks());
        plugin.getCommand("speed").setExecutor(new Speed());
        plugin.getCommand("serialitem").setExecutor(new ItemSerialCMD());
        plugin.getCommand("points").setExecutor(new PointsManagerCMD());
        plugin.getCommand("srv").setExecutor(new ServersManagerOpenGui());
    }

    /**
     *      Load all location labels from the SQL
     */
    public void loadLabels() {
        try {
            Connection connection = MainLibs.getSql();
            PreparedStatement q = connection.prepareStatement("SELECT * FROM `balkoura_label_locations`;");
            q.execute();
            while (q.getResultSet().next()) MainCore.locLabels.put(q.getResultSet().getString("name"),
                    LocationParser.getLocation(q.getResultSet().getString("world"),
                            q.getResultSet().getString("location")));
            q.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
