package fr.milekat.cite_core.core.commands;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_core.core.events.PlayerDeathInventory;
import fr.milekat.cite_libs.MainLibs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class LastInventoryCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(label.equalsIgnoreCase("getlastinv")){
            if (sender instanceof Player){
                Player p = (Player) sender;
                if (args.length == 1){
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    ItemStack[] inv = PlayerDeathInventory.getLastInventory(target.getUniqueId());
                    if (inv.length == 0){
                        p.sendMessage(MainCore.prefixCmd + "§cJoueur introuvable ou inventaire vide !");
                        return false;
                    }
                    ItemStack[] armorInventory = new ItemStack[] {inv[0], inv[1], inv[2], inv[3]};
                    p.getInventory().setArmorContents(armorInventory);
                    for (int i = 4; i < inv.length; i++) {
                        p.getInventory().setItem(i-4, inv[i]);
                    }
                    p.sendMessage(MainCore.prefixCmd + "§fInventaire défini !");
                } else {
                    p.sendMessage(MainCore.prefixCmd + "§cVous devez préciser le nom d'un joueur !");
                }
            } else {
                sender.sendMessage(MainLibs.prefixConsole + "Vous devez être un joueur !");
                return false;
            }
        }

        return false;
    }
}
