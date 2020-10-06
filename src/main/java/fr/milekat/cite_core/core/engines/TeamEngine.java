package fr.milekat.cite_core.core.engines;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_core.core.obj.Profil;
import fr.milekat.cite_core.core.obj.Team;
import fr.milekat.cite_libs.utils_tools.Tools;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeamEngine {
    /**
     *      Routine de création / màj des équipes
     */
    public BukkitTask runTask() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                updateTeams();
            }
        }.runTaskTimerAsynchronously(MainCore.getInstance(),600L,600L);
    }

    public void updateTeams() {
        Connection connection = MainCore.getSQL().getConnection();
        try {
            PreparedStatement q = connection.prepareStatement("SELECT * FROM `" + MainCore.SQLPREFIX + "team`;");
            q.execute();
            while (q.getResultSet().next()) {
                PreparedStatement q2 = connection.prepareStatement("SELECT `uuid` FROM `" + MainCore.SQLPREFIX +
                        "player` WHERE `team_id` = ?;");
                q2.setInt(1,q.getResultSet().getInt("team_id"));
                q2.execute();
                ArrayList<Profil> members = new ArrayList<>();
                while (q2.getResultSet().next()) {
                    members.add(MainCore.profilHashMap.
                            get(UUID.fromString(q2.getResultSet().getString("uuid"))));
                }
                q2.close();
                MainCore.teamHashMap.put(q.getResultSet().getInt("team_id"),
                        new Team(q.getResultSet().getInt("team_id"),
                                q.getResultSet().getString("team_name"),
                                q.getResultSet().getString("team_tag"),
                                q.getResultSet().getInt("money"),
                                members,
                                getMapTradesUses(q.getResultSet().getString("team_trades_uses"))));
            }
            q.close();
        } catch (SQLException throwables) {
            Bukkit.getLogger().warning("Impossible d'update la liste des équipes !");
            throwables.printStackTrace();
        }
    }

    public void saveTradesUses(Team team) {
        try {
            Connection connection = MainCore.getSQL().getConnection();
            PreparedStatement q = connection.prepareStatement(
                    "UPDATE `balkoura_team` SET `team_trades_uses`= ? WHERE `team_id` = ?;");
            q.setString(1, getStringTradesUses(team.getTradesuses()));
            q.setInt(2, team.getId());
            q.execute();
            q.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private HashMap<Integer, Integer> getMapTradesUses(String trades) {
        HashMap<Integer, Integer> tradesuses = new HashMap<>();
        if (trades!=null) {
            for (String trade : trades.split(";")) {
                String[] tradesplit = trade.split(":");
                tradesuses.put(Integer.parseInt(tradesplit[0]), Integer.parseInt(tradesplit[1]));
            }
        }
        return tradesuses;
    }

    private String getStringTradesUses(HashMap<Integer, Integer> tradesuses) {
        StringBuilder trades = new StringBuilder();
        for (Map.Entry<Integer, Integer> loop : tradesuses.entrySet()) {
            trades.append(loop.getKey()).append(":").append(loop.getValue()).append(";");
        }
        return Tools.remLastChar(trades.toString());
    }
}
