package fr.milekat.cite_core.core.bungee;

import fr.milekat.cite_core.MainCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvents implements Listener {
    @EventHandler
    public void JoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        /*event.getPlayer().sendMessage(MainCore.prefixCmd + "§6Vous voici sur " +
                MainLibs.getInstance().getConfig().getString("other.join_server_name"));
        Profil profil = getPlayerProfil(event.getPlayer().getUniqueId());
        if (profil!=null && profil.isScoreboard()) {
            MainCore.boards.put(event.getPlayer().getUniqueId(), new FastBoard(event.getPlayer()));
        }*/
    }

    @EventHandler
    public void QuitEvent(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        MainCore.boards.remove(event.getPlayer().getUniqueId());
    }

    /*
    /**
     *      Création d'un profil
     */
    /*private Profil getPlayerProfil(UUID uuid) {
        Connection connection = MainCore.getSQL().getConnection();
        try {
            PreparedStatement q = connection.prepareStatement("SELECT `name`, `team_id`, " +
                    "`chat_mode`, `muted`, `banned`, `reason`, `modson`, `buildon`, " +
                    "`maintenance`, `discord_id`, `crates` FROM `" + MainCore.SQLPREFIX + "player` WHERE `uuid` = ?;");
            q.setString(1, uuid.toString());
            q.execute();
            while (q.getResultSet().next()) {
                HashMap<Integer, Integer> crates = new HashMap<>();
                for (String loop : q.getResultSet().getString("crates").split(";")) {
                    int[] cratessplit = Arrays.stream(loop.split(":")).mapToInt(Integer::parseInt).toArray();
                    crates.put(cratessplit[0],cratessplit[1]);
                }
                MainCore.profilHashMap.put(uuid,
                        new Profil(uuid,
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
                MainCore.joueurslist.put(q.getResultSet().getString("name"),uuid);
            }
            q.close();
            return MainCore.profilHashMap.get(uuid);
        } catch (SQLException throwables) {
            Bukkit.getLogger().warning("Impossible d'update le joueur: " + uuid);
            throwables.printStackTrace();
        }
        return null;
    }*/
}
