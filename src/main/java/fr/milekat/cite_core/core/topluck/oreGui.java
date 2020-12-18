package fr.milekat.cite_core.core.topluck;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class oreGui extends FastInv {
    public oreGui(Player player, Material material, HashMap<Material, HashMap<String, Integer>> mined) {
        super(54, "TopLuck");
        if (mined==null) return;
        int slot = 0;
        for (Material loop : mined.keySet()) {
            if (loop.equals(Material.STONE)) continue;
            setItem(slot, new ItemBuilder(loop).build(), e -> new oreGui(player, loop, mined));
            slot++;
        }
        setItems(9,17, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(" ").build());
        TreeMap<String, Double> players = new TreeMap<>();
        HashMap<String, Integer> stone = mined.getOrDefault(Material.STONE,null);
        if (stone==null) return;
        for (Map.Entry<String, Integer> loop : mined.get(material).entrySet()) {
            Integer pstone = stone.getOrDefault(loop.getKey(),1);
            players.put(loop.getKey(), Double.valueOf(loop.getValue()) / Double.valueOf(pstone));
        }
        slot = 18;
        for (Map.Entry<String, Double> sortedPlayers : sortByValues(players).entrySet()) {
            setItem(slot, new ItemBuilder(material)
                    .name(sortedPlayers.getKey())
                    .addLore(String.valueOf(mined.get(material).get(sortedPlayers.getKey())))
                    .addLore(new DecimalFormat("#.##").format(sortedPlayers.getValue()*100) + "%")
                    .build());
            slot++;
            if (slot > 35) break;
        }
        open(player);
    }

    private <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = (k2, k1) -> {
            int compare = map.get(k1).compareTo(map.get(k2));
            if (compare == 0)
                return 1;
            else
                return compare;
        };
        Map<K, V> sortedByValues = new TreeMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
