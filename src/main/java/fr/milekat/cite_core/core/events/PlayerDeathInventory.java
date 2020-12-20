package fr.milekat.cite_core.core.events;

import com.google.common.collect.ObjectArrays;
import fr.milekat.cite_libs.MainLibs;
import fr.milekat.cite_libs.utils_tools.ItemSerial;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerDeathInventory implements Listener {

    @EventHandler
    public void playerDeath(PlayerDeathEvent e){
        ItemStack[] armors = e.getEntity().getInventory().getArmorContents();
        ItemStack[] inventory = e.getEntity().getInventory().getContents();
        ItemStack[] fullInventory = ObjectArrays.concat(armors, inventory, ItemStack.class);
        updateInventory(e.getEntity().getUniqueId(), ItemSerial.invToBase64(fullInventory));
    }

    public static void updateInventory(UUID uuid, String inventory) {
        Connection connection = MainLibs.getSql();
        PreparedStatement q = null;
        try {
            q = connection.prepareStatement("INSERT INTO `balkoura_death_inventory`" +
                    "(`player_id`, `inventory`) VALUES (" +
                    "(SELECT `player_id` FROM `balkoura_player` WHERE `uuid` = '" + uuid.toString() + "'), ?) " +
                    "ON DUPLICATE KEY UPDATE `inventory` = ?;");
            q.setString(1, inventory);
            q.setString(1, inventory);
            q.execute();
            q.close();
        } catch (SQLException throwables) {
            Bukkit.getLogger().warning(MainLibs.prefixConsole + "Impossible d'update l'inventaire lors de la mort de " + uuid);
            throwables.printStackTrace();
        }
    }

    public static ItemStack[] getLastInventory(UUID uuid) {
        Connection connection = MainLibs.getSql();
        String inventory = "";
        PreparedStatement q = null;
        try {
            q = connection.prepareStatement("SELECT * FROM `balkoura_death_inventory` WHERE `player_id` = " +
                    "(SELECT `player_id` FROM `balkoura_player` WHERE `uuid` = '" + uuid.toString() + "');");
            q.execute();
            if (q.getResultSet().first()) {
                inventory = q.getResultSet().getString("inventory");
            }
            q.close();
        } catch (SQLException throwables) {
            Bukkit.getLogger().warning(MainLibs.prefixConsole + "Impossible de récupérer l'inventaire lors de " + uuid);
            throwables.printStackTrace();
        }

        return ItemSerial.invFromBase64(inventory);
    }

}
