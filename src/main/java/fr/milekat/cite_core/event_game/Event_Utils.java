package fr.milekat.cite_core.event_game;

import fr.milekat.cite_core.MainCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Event_Utils {
    /**
     *      Si le joueur est connecté, ferme son Event Box (si il l'a ouvert)
     * @param player cible
     */
    public static void closeEventBox(String player){
        for (Player onlineP : Bukkit.getOnlinePlayers()){
            if (onlineP.getName().equalsIgnoreCase(player)) {
                if (onlineP.getOpenInventory().getTitle().equalsIgnoreCase(MainCore.getEvents().boxPlayer)) {
                    onlineP.sendMessage(MainCore.prefixCmd + "§6Un instant, le staff modifie ton Event Box !§c.");
                    onlineP.closeInventory();
                }
            }
        }
    }
}
