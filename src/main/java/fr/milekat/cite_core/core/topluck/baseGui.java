package fr.milekat.cite_core.core.topluck;

import fr.milekat.cite_libs.MainLibs;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class baseGui extends FastInv {
    public baseGui(Player player) {
        super(54, "TopLuck");
        HashMap<Material, HashMap<String, Integer>> mined = getMined();
        if (mined==null) return;
        int slot = 0;
        for (Material loop : mined.keySet()) {
            if (loop.equals(Material.STONE)) continue;
            setItem(slot, new ItemBuilder(loop).build(),e -> new oreGui(player, loop, mined));
            slot++;
        }
        setItems(9,17, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        open(player);
    }

    /**
     *      Récupération des blocs minés
     */
    private HashMap<Material, HashMap<String, Integer>> getMined() {
        try {
            Connection connection = MainLibs.getSql();
            PreparedStatement q = connection.prepareStatement("SELECT * FROM (" +
                    "SELECT u.user, REPLACE(REPLACE(REPLACE(REPLACE(UPPER(REPLACE(m.material,'minecraft:','')),'DIORITE','STONE'),'GRANITE', 'STONE'), 'ANDESITE', 'STONE'), 'COBBLESTONE', 'STONE') as material, COUNT(b.user) as mined " +
                    "FROM `co_sydney_user` u LEFT JOIN `co_sydney_block` b ON u.rowid=b.user " +
                    "LEFT JOIN `co_sydney_material_map` m ON b.type=m.id WHERE u.`user` NOT LIKE '#%' AND " +
                    "(m.material LIKE 'minecraft:%_ore' OR m.material LIKE 'minecraft:stone') AND m.material NOT LIKE 'minecraft:nether%' GROUP BY u.user, m.material " +
                    "UNION " +
                    "SELECT u.user, REPLACE(REPLACE(REPLACE(REPLACE(UPPER(REPLACE(m.material,'minecraft:','')),'DIORITE','STONE'),'GRANITE', 'STONE'), 'ANDESITE', 'STONE'), 'COBBLESTONE', 'STONE') as material, COUNT(b.user) as mined " +
                    "FROM `co_prague_user` u LEFT JOIN `co_prague_block` b ON u.rowid=b.user " +
                    "LEFT JOIN `co_prague_material_map` m ON b.type=m.id WHERE u.`user` NOT LIKE '#%' AND " +
                    "(m.material LIKE 'minecraft:%_ore' OR m.material LIKE 'minecraft:stone') AND m.material NOT LIKE 'minecraft:nether%' GROUP BY u.user, m.material" +
                    ") AS temp GROUP BY user, material ORDER BY user ASC;");
            q.execute();
            HashMap<Material, HashMap<String, Integer>> returnedmap = new HashMap<>();
            while (q.getResultSet().next()) {
                Material material = Material.getMaterial(q.getResultSet().getString("material"));
                HashMap<String, Integer> playerMined = returnedmap.getOrDefault(material, new HashMap<>());
                playerMined.put(q.getResultSet().getString("user"),q.getResultSet().getInt("mined"));
                returnedmap.put(material,playerMined);
            }
            q.close();
            return returnedmap;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
