package fr.milekat.cite_core;

import fr.milekat.cite_core.chat.ChatInsert;
import fr.milekat.cite_core.core.bungee.ServersUpdateEngine;
import fr.milekat.cite_core.core.commands.chest.LootCrates;
import fr.milekat.cite_core.core.commands.chest.LootCratesTAB;
import fr.milekat.cite_core.core.crafts.CraftManager;
import fr.milekat.cite_core.core.engines.PlayersEngine;
import fr.milekat.cite_core.core.engines.TeamEngine;
import fr.milekat.cite_core.core.obj.Profil;
import fr.milekat.cite_core.core.obj.Team;
import fr.milekat.cite_core.core.utils.PluginLoader;
import fr.milekat.cite_core.event_game.Event_Init;
import fr.milekat.cite_core.event_game.Event_Tab;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainCore extends JavaPlugin {
    // Init des var static, pour tous le projet
    public static String prefixCmd = "§6[§2Balkoura§6]§r ";
    public static String erreurSQLMsg = prefixCmd + "§cErreur interne, contacte un admin.";
    // Vars pour Main
    private BukkitTask profilesTask;
    private BukkitTask teamTask;
    private static MainCore mainCore;
    private static Event_Init events;
    // Core
    public static Location defaultLocation = new Location(Bukkit.getWorld("wolrd"),0,150,0);
    public static HashMap<UUID, Profil> profilHashMap = new HashMap<>();
    public static HashMap<String, UUID> joueurslist = new HashMap<>();
    public static HashMap<Integer, Team> teamHashMap = new HashMap<>();
    public static HashMap<String, Integer> serveurPlayers = new HashMap<>();
    public static HashMap<String, Location> locLabels = new HashMap<>();
    public static String SQLPREFIX = "balkoura_";
    public static String SRVNAME = "Balkoura";
    private BukkitTask serversUpdate;

    @Override
    public void onEnable() {
        mainCore = this;
        SRVNAME = MainLibs.getInstance().getConfig().getString("other.server_name");
        // Event_Game load
        events = new Event_Init();
        new PlayersEngine().updateProfiles();
        new TeamEngine().updateTeams();
        // Events
        PluginLoader pluginLoader = new PluginLoader();
        pluginLoader.loadEvents(this);
        // Commandes
        pluginLoader.loadCommands(this);
        // LocsLabels
        pluginLoader.loadLabels();
        // Bungee Messaging
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        // Tab
        getCommand("event").setTabCompleter(new Event_Tab());
        getCommand("crate").setTabCompleter(new LootCratesTAB());
        // Craft
        new CraftManager().loadCraft();
        // Engines
        profilesTask = new PlayersEngine().runTask();
        teamTask = new TeamEngine().runTask();
        serversUpdate = new ServersUpdateEngine().runTask();
    }

    @Override
    public void onDisable() {
        // Craft
        new CraftManager().unLoadCraft();
        profilesTask.cancel();
        teamTask.cancel();
        serversUpdate.cancel();
    }

    public static MainCore getInstance(){
        return mainCore;
    }

    public static Event_Init getEvents() {
        return events;
    }

    public static ArrayList<String> getPlayersList(String arg) {
        ArrayList<String> names = new ArrayList<>();
        for (Profil profil : MainCore.profilHashMap.values()) {
            names.add(profil.getName());
        }
        return getTabArgs(arg, names);
    }

    public static ArrayList<String> getTabArgs(String arg, List<String> MyStrings) {
        ArrayList<String> MySortStrings = new ArrayList<>();
        for(String loop : MyStrings) {
            if(loop.toLowerCase().startsWith(arg.toLowerCase()))
            {
                MySortStrings.add(loop);
            }
        }
        return MySortStrings;
    }

    public static void sendAnnonce(String msg) {
        JedisPub.sendRedis("new_msg#:#" + ChatInsert.insertSQLNewChat(msg));
    }
}
