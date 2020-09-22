package fr.milekat.cite_core.core.commands;

import fr.milekat.cite_core.MainCore;
import fr.milekat.cite_libs.utils_tools.ItemSerial;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemSerialCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length==0) {
            sender.sendMessage(ItemSerial.itemToBase64(((Player) sender).getInventory().getItemInMainHand()));
        } else {
            try {
                ItemStack itemStack = ItemSerial.itemFromBase64(args[0]);
                ((Player) sender).getInventory().addItem(itemStack);
                sender.sendMessage(MainCore.prefixCmd + "§b" + itemStack.getAmount() + "§6x§b" + itemStack.getType() +
                        " §6ajouté à votre inv.");
            } catch (IllegalArgumentException exception) {
                sender.sendMessage(MainCore.prefixCmd + "§cItem inconnue impossible de le give");
            }
        }
        return true;
    }
}
