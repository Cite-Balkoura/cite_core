package fr.milekat.cite_core.core.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.milekat.cite_core.MainCore;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.Normalizer;

public class BungeeSendPlayer implements Listener {
    @EventHandler
    public void onSignClick(PlayerInteractEvent event){
        if (event.getClickedBlock()==null) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!(event.getClickedBlock().getBlockData() instanceof Sign)) return;
        Sign sign = (Sign) event.getClickedBlock().getState();
        if (!sign.getLine(0).equalsIgnoreCase("§8[§aTéléportation§8]")) return;
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(ChatColor.stripColor(stripAccents(sign.getLine(1))));
        event.getPlayer().sendPluginMessage(MainCore.getInstance(), "BungeeCord", out.toByteArray());
    }

    private String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }
}
