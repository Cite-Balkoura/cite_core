package fr.milekat.cite_core.event_game;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.ItemSerial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import static fr.milekat.cite_libs.utils_tools.Jedis.JedisPub.sendRedis;

public class Event_Cmd implements CommandExecutor {
    /**
     *      Commande /event
     */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("event")){
            if (!(sender instanceof Player)) return true;
            Player p = (Player) sender;
            Connection connection = MainLibs.getSql();
            if (args.length<1) {
                sendHelpEvent((Player) sender);
                return true;
            }
            switch (args[0].toLowerCase())
            {
                case "box":
                {
                    try {
                        PreparedStatement q = connection.prepareStatement("SELECT `event_box`, `lock_event_box` " +
                                "FROM `" + MainCore.SQLPREFIX + "player` WHERE `uuid` = ?");
                        q.setString(1,p.getUniqueId().toString());
                        q.execute();
                        q.getResultSet().last();
                        if (q.getResultSet().getBoolean("lock_event_box")) {
                            p.sendMessage(MainCore.prefixCmd + "§cUn instant, §6le staff modifie ton Event Box !");
                        } else {
                            Inventory eventBox = Bukkit.createInventory(null,27, MainCore.getEvents().boxPlayer);
                            eventBox.setContents(ItemSerial.invFromBase64(q.getResultSet().getString("event_box")));
                            p.openInventory(eventBox);
                        }
                        q.close();
                    } catch (SQLException e) {
                        Bukkit.getLogger().warning(MainLibs.prefixConsole +
                                "Erreur SQL impossible de récupérer la box de "+ sender.getName() + " erreur : " + e);
                        e.printStackTrace();
                    }
                    break;
                }
                case "getbox":
                {
                    if (!sender.hasPermission("modo.core.event.getbox")){
                        sender.sendMessage(MainCore.prefixCmd + "§cCommande de modération.");
                        return true;
                    }
                    if (args.length<2) {
                        sendHelpEvent((Player) sender);
                        return true;
                    }
                    ItemStack getBox = MainCore.getEvents().eggBoxEmpty.clone();
                    ItemMeta metaBox = getBox.getItemMeta();
                    metaBox.setDisplayName(args[1]);
                    getBox.setItemMeta(metaBox);
                    ((Player) sender).getInventory().addItem(getBox);
                    break;
                }
                case "seebox":
                {
                    if (!sender.hasPermission("modo.core.event.seebox")){
                        sender.sendMessage(MainCore.prefixCmd + "§cCommande de modération.");
                        return true;
                    }
                    if (args.length<2) {
                        sendHelpEvent((Player) sender);
                        return true;
                    }
                    try {
                        PreparedStatement q = connection.prepareStatement("SELECT `event_box` FROM `" + MainCore.SQLPREFIX
                                + "player` WHERE `name` = ?");
                        q.setString(1,args[1]);
                        q.execute();
                        q.getResultSet().last();
                        Inventory eventBox = Bukkit.createInventory(null,27,
                                MainCore.getEvents().boxModo + "see " + args[1]);
                        eventBox.setContents(ItemSerial.invFromBase64(q.getResultSet().getString("event_box")));
                        p.openInventory(eventBox);
                        q.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                }
                case "give":
                {
                    if (!sender.hasPermission("modo.core.event.give")){
                        sender.sendMessage(MainCore.prefixCmd + "§cCommande de modération.");
                        return true;
                    }
                    if (args.length<2) {
                        sendHelpEvent((Player) sender);
                        return true;
                    }
                    if (((Player) sender).getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        sender.sendMessage(MainCore.prefixCmd + "§cMerci de tenir un item en main !");
                        return true;
                    }
                    sendRedis("closeeventbox#:#" + args[1]);
                    try {
                        PreparedStatement q = connection.prepareStatement("SELECT `event_box` FROM `" + MainCore.SQLPREFIX
                                + "player` WHERE `name` = ?;");
                        q.setString(1, args[1]);
                        q.execute();
                        q.getResultSet().last();
                        Inventory box = Bukkit.createInventory(null, 27, "box");
                        box.setContents(ItemSerial.invFromBase64(q.getResultSet().getString("event_box")));
                        q.close();
                        final Map<Integer, ItemStack> map = box.addItem(((Player) sender).getInventory().getItemInMainHand());
                        if (!map.isEmpty()) {
                            sender.sendMessage(MainCore.prefixCmd + "§4ABORT: §cEvent box de §b" + args[1] + " §cFull !");
                        } else {
                            q = connection.prepareStatement("UPDATE `player` SET `event_box` = ? WHERE `name` = ?;");
                            q.setString(1,ItemSerial.invToBase64(box.getContents()));
                            q.setString(2,args[1]);
                            q.execute();
                            q.close();
                        }
                    } catch (SQLException throwables) {
                        Bukkit.getLogger().warning(MainCore.prefixCmd + "Impossible de give un item à " + args[1]);
                        throwables.printStackTrace();
                    }
                    break;
                }
                default:
                {
                    sendHelpEvent((Player) sender);
                }
            }
            return true;
        }
        return false;
    }

    /**
     *      Envoie la liste d'help pour la commande /event au joueur qui exécute /event (Custom pour les modos)
     * @param player joueur qui exécute /event
     */
    private void sendHelpEvent(Player player){
        player.sendMessage(MainCore.prefixCmd + "§6Event help !");
        player.sendMessage("§6/event help: §rAffiche ces informations.");
        player.sendMessage("§6/event box: §rOuvre votre box, cliquer sur un Item/Stack pour le récupérer.");
        if (player.hasPermission("modo.core.event.getbox")) {
            player.sendMessage("§6/event getbox <player>: §rGive un oeuf pour poser la box d'un joueur au sol.");
            player.sendMessage("§cATTENTION, les hoppers peuvent remplir cette box.");
        }
        if (player.hasPermission("modo.core.event.seebox")) {
            player.sendMessage("§6/event seebox <player>: §rPermet de voir le contenu d'une box d'un joueur.");
        }
        if (player.hasPermission("modo.core.event.give")) {
            player.sendMessage("§6/event give <player>: §rPermet de give l'item dans votre main au joueur (En event box).");
        }
    }
}
