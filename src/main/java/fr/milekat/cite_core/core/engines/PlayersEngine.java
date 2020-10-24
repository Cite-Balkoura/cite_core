package fr.milekat.cite_core.core.engines;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_core.core.obj.Profil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class PlayersEngine {
    /**
     *      Routine de création / màj des Profiles
     */
    public BukkitTask runTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                updateProfiles();
            }
        }.runTaskTimerAsynchronously(MainCore.getInstance(),600L,600L);
    }

    public void updateProfiles(){
        Connection connection = MainCore.getSQL().getConnection();
        try {
            PreparedStatement q = connection.prepareStatement(
                    "SELECT  `uuid`, `name`, `team_id`, `chat_mode`, `muted`, `banned`, `reason`, " +
                            "`modson`, `buildon`, `maintenance`, `discord_id`, `crates` " +
                            "FROM `" + MainCore.SQLPREFIX + "player` WHERE `name` != 'Annonce';");
            q.execute();
            while (q.getResultSet().next()) {
                HashMap<Integer, Integer> crates = new HashMap<>();
                for (String loop : q.getResultSet().getString("crates").split(";")) {
                    int[] cratessplit = Arrays.stream(loop.split(":")).mapToInt(Integer::parseInt).toArray();
                    crates.put(cratessplit[0],cratessplit[1]);
                }
                MainCore.profilHashMap.put(UUID.fromString(q.getResultSet().getString("uuid")),
                        new Profil(UUID.fromString(q.getResultSet().getString("uuid")),
                                q.getResultSet().getString("name"),
                                q.getResultSet().getInt("team_id"),
                                q.getResultSet().getInt("chat_mode"),
                                q.getResultSet().getString("muted"),
                                q.getResultSet().getString("banned"),
                                q.getResultSet().getString("reason"),
                                q.getResultSet().getBoolean("modson"),
                                q.getResultSet().getBoolean("buildon"),
                                q.getResultSet().getBoolean("maintenance"),
                                q.getResultSet().getLong("discord_id"),
                                crates));
                MainCore.joueurslist.put(q.getResultSet().getString("name"),
                        UUID.fromString(q.getResultSet().getString("uuid")));
            }
            q.close();
        } catch (SQLException throwables) {
            Bukkit.getLogger().warning("Impossible d'update la liste des joueurs !");
            throwables.printStackTrace();
        }
    }
}
