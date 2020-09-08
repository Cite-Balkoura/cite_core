package fr.milekat.cite_core.event_game;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.utils_tools.ItemSerial;
import fr.milekat.cite_libs.utils_tools.Jedis.JedisPub;
import fr.milekat.cite_libs.utils_tools.Tools;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Event_Event implements Listener {
    /**
     *      Quand un modo place une box (Avec l'Oeuf)
     */
    @EventHandler
    private void onPlaceBox(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        // Check que ce soit un modo + avec l'oeuf !
        if (!player.hasPermission("modo.core.event.placebox")) return;
        if (!tool.getType().equals(MainCore.getEvents().eggBoxEmpty.getType())) return;
        if (!tool.getItemMeta().getLore().equals(MainCore.getEvents().eggBoxEmpty.getItemMeta().getLore())) return;
        if (event.getClickedBlock()==null) return;
        event.setCancelled(true);
        Block block = event.getClickedBlock().getRelative(event.getBlockFace());
        Connection connection = MainCore.getSQL().getConnection();
        try {
            PreparedStatement q = connection.prepareStatement(
                    "SELECT `event_box`, `lock_event_box` FROM `" + MainCore.SQLPREFIX + "player` WHERE `name` = ?;");
            q.setString(1,tool.getItemMeta().getDisplayName());
            JedisPub.sendRedis("closeeventbox#:#"+tool.getItemMeta().getDisplayName());
            q.execute();
            q.getResultSet().last();
            if (q.getResultSet().getBoolean("lock_event_box")){
                player.sendMessage(MainCore.prefixCmd + "§cCette box est déjà utilisée !");
                q.close();
                return;
            }
            PreparedStatement q2 = connection.prepareStatement("UPDATE `" + MainCore.SQLPREFIX +
                    "player` SET `lock_event_box` = true WHERE `name` = ?;");
            q2.setString(1,tool.getItemMeta().getDisplayName());
            q2.execute();
            q2.close();
            block.setType(Material.SHULKER_BOX);
            block = event.getClickedBlock().getRelative(event.getBlockFace());
            ShulkerBox shulkerBox = (ShulkerBox) block.getState();
            shulkerBox.setCustomName(MainCore.getEvents().boxModo + tool.getItemMeta().getDisplayName());
            shulkerBox.getSnapshotInventory().setContents(
                    ItemSerial.invFromBase64(q.getResultSet().getString("event_box")));
            shulkerBox.update();
            q.close();
            player.sendMessage(MainCore.prefixCmd + "§6Event Box de §b" + tool.getItemMeta().getDisplayName() +
                    " §6placée, casser la box pour sauvegarder.");
        } catch (SQLException throwables) {
            player.sendMessage(MainCore.prefixCmd + "§cErreur SQL");
            throwables.printStackTrace();
        }
    }

    /**
     *      Évite qu'un joueur clique sur la box !
     */
    @EventHandler
    private void boxClick(InventoryOpenEvent event){
        if (event.getView().getTitle().contains(MainCore.getEvents().boxModo)) {
            if (!event.getPlayer().hasPermission("modo.core.event.boxopen")) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(MainCore.prefixCmd + "§Ceci est un outil du staff, pas touche !");
            }
        }
    }

    /**
     *      Sauvegarde de la box si break par un modo
     */
    @EventHandler
    private void breakShulker(BlockBreakEvent event){
        if (event.getBlock().getType().equals(Material.SHULKER_BOX)) {
            if (event.getBlock().getState() instanceof ShulkerBox) {
                ShulkerBox shulkerBox = (ShulkerBox) event.getBlock().getState();
                if (shulkerBox.getCustomName()==null) return;
                if (shulkerBox.getCustomName().contains(MainCore.getEvents().boxModo)) {
                    if (event.getPlayer().hasPermission("modo.core.event.boxbreak")){
                        Connection connection = MainCore.getSQL().getConnection();
                        String name = shulkerBox.getCustomName();
                        try {
                            PreparedStatement q = connection.prepareStatement("UPDATE `" + MainCore.SQLPREFIX +
                                    "player` SET `lock_event_box` = '0', `event_box` = ? WHERE `name` = ?;");
                            q.setString(1, ItemSerial.invToBase64(shulkerBox.getInventory().getContents()));
                            q.setString(2, name.replace(MainCore.getEvents().boxModo,""));
                            q.execute();
                            q.close();
                            event.setCancelled(true);
                            event.getBlock().setType(Material.AIR);
                            event.getPlayer().sendMessage(MainCore.prefixCmd + "§6Box de §b"+name.replace(
                                    MainCore.getEvents().boxModo,"") +"§6 sauvegardée.");
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    } else {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(MainCore.prefixCmd + "§Ceci est un outil du staff, pas touche !");
                    }
                }
            }
        }
    }

    /**
     *      Event de récupération des items de la box du joueur
     */
    @EventHandler
    private void onClickBoxEvent(InventoryClickEvent event){
        if (event.getView().getTitle().equalsIgnoreCase(MainCore.getEvents().boxPlayer)) {
            if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
            event.setCancelled(true);
            if (event.getCurrentItem()==null) return;
            if (!event.getCursor().getType().equals(Material.AIR)) return;
            if (Tools.canStore(event.getWhoClicked().getInventory(),36,event.getCurrentItem(),1)){
                try {
                    Connection connection = MainCore.getSQL().getConnection();
                    ItemStack item = event.getCurrentItem();
                    event.getView().setItem(event.getSlot(),null);
                    PreparedStatement q = connection.prepareStatement("UPDATE `" + MainCore.SQLPREFIX + "player` " +
                            "SET `event_box` = ? WHERE `uuid` = ?;");
                    q.setString(1, ItemSerial.invToBase64(event.getView().getTopInventory().getContents()));
                    q.setString(2, event.getWhoClicked().getUniqueId().toString());
                    q.execute();
                    q.close();
                    event.getWhoClicked().getInventory().addItem(item);
                } catch (SQLException throwables) {
                    event.getWhoClicked().sendMessage(MainCore.prefixCmd + "§cNous rencontrons un soucis avec les Box.");
                    event.getWhoClicked().closeInventory();
                    throwables.printStackTrace();
                }

            } else {
                event.getWhoClicked().sendMessage(MainCore.prefixCmd + "§cInventaire full.");
            }
        }
        if (event.getView().getTitle().equalsIgnoreCase(MainCore.getEvents().boxModo +"see ")) {
            if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) return;
            event.setCancelled(true);
        }
    }
}
