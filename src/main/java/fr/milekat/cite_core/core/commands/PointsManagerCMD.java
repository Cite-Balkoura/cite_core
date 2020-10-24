package fr.milekat.cite_core.core.commands;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_core.core.utils.EventPoints;
import fr.milekat.cite_core.core.utils.QuestPoints;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PointsManagerCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 4) {
            sendHelp(sender, label);
            return true;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player==null) {
            sender.sendMessage(MainCore.prefixCmd + "§cJoueur non connecté.");
            sender.sendMessage("§6/" + label + " <event/quest> <joueur>");
        } else {
            int points;
            try {
                points = Integer.parseInt(args[3]);
            } catch (NumberFormatException exception)  {
                sender.sendMessage(MainCore.prefixCmd + "§cMerci de mettre un nombre de point(s) entier.");
                return true;
            }
            try {
                if (args[0].equalsIgnoreCase("event")) {
                    if (args[2].equalsIgnoreCase("add")) {
                        EventPoints.addPoint(player.getUniqueId(), points);
                        sender.sendMessage(MainCore.prefixCmd + "§6Points ajoutés.");
                    } else if (args[2].equalsIgnoreCase("remove")) {
                        EventPoints.removePoint(player.getUniqueId(), points);
                        sender.sendMessage(MainCore.prefixCmd + "§6Points retirés.");
                    } else if (args[2].equalsIgnoreCase("set")) {
                        EventPoints.setPoint(player.getUniqueId(), points);
                        sender.sendMessage(MainCore.prefixCmd + "§6Points définis.");
                    } else sendHelp(sender, label);
                } else if (args[0].equalsIgnoreCase("quest")) {
                    if (args[2].equalsIgnoreCase("add")) {
                        QuestPoints.addPoint(player.getUniqueId(), points);
                        sender.sendMessage(MainCore.prefixCmd + "§6Points ajoutés.");
                    } else if (args[2].equalsIgnoreCase("remove")) {
                        QuestPoints.removePoint(player.getUniqueId(), points);
                        sender.sendMessage(MainCore.prefixCmd + "§6Points retirés.");
                    } else if (args[2].equalsIgnoreCase("set")) {
                        QuestPoints.setPoint(player.getUniqueId(), points);
                        sender.sendMessage(MainCore.prefixCmd + "§6Points définis.");
                    } else sendHelp(sender, label);
                } else sendHelp(sender, label);
            } catch (SQLException throwables) {
                sender.sendMessage(MainCore.prefixCmd + "§cErreur SQL.");
                throwables.printStackTrace();
            }
        }
        return true;
    }

    private void sendHelp(CommandSender sender, String prefix) {
        sender.sendMessage(MainCore.prefixCmd + "§6Aide pour la gestion des points :");
        sender.sendMessage("§6Points §bévent§c:");
        sender.sendMessage("§6/" + prefix + " <event> <joueur> <add> <points>: \n§rAjouter des pts évent au joueur");
        sender.sendMessage("§6/" + prefix + " <event> <joueur> <remove> <points>: \n§rRetirer des pts évent au joueur");
        sender.sendMessage("§6/" + prefix + " <event> <joueur> <set> <points>: \n§rDéfini les pts évent du joueur");
        sender.sendMessage("§6Points §bquête§c:");
        sender.sendMessage("§6/" + prefix + " <quest> <joueur> <add> <points>: \n§rAjouter des pts quête au joueur");
        sender.sendMessage("§6/" + prefix + " <quest> <joueur> <remove> <points>: \n§rRetirer des pts quête au joueur");
        sender.sendMessage("§6/" + prefix + " <quest> <joueur> <set> <points>: \n§rDéfini les pts quête du joueur");
    }
}
