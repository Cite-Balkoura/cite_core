package fr.milekat.cite_core;

import fr.milekat.cite_core.chat.ChatInsert;
import fr.milekat.cite_core.core.bungee.BungeeSendPlayer;
import fr.milekat.cite_core.core.bungee.JoinQuitEvents;
import fr.milekat.cite_core.core.commands.SignEdit;
import fr.milekat.cite_core.core.commands.Speed;
import fr.milekat.cite_core.core.commands.WebLinks;
import fr.milekat.cite_core.core.engines.PlayersEngine;
import fr.milekat.cite_core.core.engines.TeamEngine;
import fr.milekat.cite_core.core.events.RedisMessage;
import fr.milekat.cite_core.core.obj.Profil;
import fr.milekat.cite_core.core.obj.Team;
import fr.milekat.cite_core.event_game.Event_Cmd;
import fr.milekat.cite_core.event_game.Event_Event;
import fr.milekat.cite_core.event_game.Event_Init;
import fr.milekat.cite_core.event_game.Event_Tab;
import fr.milekat.cite_core.utils_tools.Boards.FastBoard;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import fr.milekat.cite_libs.utils_tools.MariaManage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

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
    public static HashMap<UUID, FastBoard> boards = new HashMap<>();
    public static HashMap<UUID, Profil> profilHashMap = new HashMap<>();
    public static HashMap<String, UUID> joueurslist = new HashMap<>();
    public static HashMap<Integer, Team> teamHashMap = new HashMap<>();
    public static String SQLPREFIX = "balkoura_";

    @Override
    public void onEnable() {
        mainCore = this;
        profilesTask = new PlayersEngine().runTask();
        teamTask = new TeamEngine().runTask();
        // Event_Game load
        events = new Event_Init();
        // Events
        getServer().getPluginManager().registerEvents(new JoinQuitEvents(),this);
        getServer().getPluginManager().registerEvents(new Event_Event(),this);
        getServer().getPluginManager().registerEvents(new RedisMessage(),this);
        getServer().getPluginManager().registerEvents(new BungeeSendPlayer(),this);
        // Bungee Messaging
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        // Commandes
        getCommand("event").setExecutor(new Event_Cmd());
        getCommand("sign").setExecutor(new SignEdit());
        getCommand("web").setExecutor(new WebLinks());
        getCommand("speed").setExecutor(new Speed());
        // Tab
        getCommand("event").setTabCompleter(new Event_Tab());
        // Scoreboard
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            MainCore.boards.put(player.getUniqueId(), new FastBoard(player));
        }
    }

    @Override
    public void onDisable() {
        profilesTask.cancel();
        teamTask.cancel();
    }

    public static MainCore getInstance(){
        return mainCore;
    }

    public static Event_Init getEvents() {
        return events;
    }

    public static MariaManage getSQL() {
        return MainLibs.sql;
    }

    public static ArrayList<String> getTabArgs(String arg, String... args) {
        List<String> MyStrings = new ArrayList<>(Arrays.asList(args));
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
