package fr.milekat.cite_core.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Speed implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && args.length == 1 && isNumeric(args[0])) {
            Player player = (Player) sender;
            if (player.isFlying()) {
                player.setFlySpeed((float) Integer.parseInt(args[0])/10);
            } else {
                player.setWalkSpeed((float) Integer.parseInt(args[0])/10);
            }
        }
        return true;
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
