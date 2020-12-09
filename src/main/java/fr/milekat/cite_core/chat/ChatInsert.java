package fr.milekat.cite_core.chat;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.DateMilekat;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChatInsert {
    /**
     *      Insert d'un nouveau MSG type annonce
     * @param msg l'annonce
     * @return l'id du msg si besoin
     */
    public static int insertSQLNewChat(String msg) {
        Connection connection = MainLibs.getSql();
        int id = 0;
        try {
            PreparedStatement q = connection.prepareStatement("INSERT INTO `" + MainCore.SQLPREFIX +
                    "chat`(`player_id`, `msg`, `msg_type`, `date_msg`) VALUES (10,?,4,?) RETURNING `msg_id`;");
            /* exe de l'instert + récupération du return */
            q.setString(1, msg);
            q.setString(2, DateMilekat.setDateNow());
            q.execute();
            q.getResultSet().next();
            id = q.getResultSet().getInt(1);
            q.close();
        } catch (SQLException throwables) {
            Bukkit.getLogger().warning("Erreur lors de l'injection d'une annonce dans le SQL.");
            throwables.printStackTrace();
        }
        return id;
    }
}
