package fr.milekat.cite_core.core.commands;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SignEdit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("sign")) {
            if (!(args.length<2)){
                if(!(Integer.parseInt(args[0])>4)) {
                    if (((Player) sender).getTargetBlock(null,10)
                            .getType().toString().toLowerCase().contains("sign")) {
                        Sign sign = (Sign) ((Player) sender).getTargetBlock(null,10).getState();
                        sign.setLine(Integer.parseInt(args[0])-1,
                                ChatColor.translateAlternateColorCodes('&', args[1]));
                        sign.update();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
