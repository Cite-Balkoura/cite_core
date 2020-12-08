package fr.milekat.cite_core.core.commands;

import fr.milekat.cite_core.core.bungee.ServersManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenServerManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        if (sender.hasPermission("modo.serveurs.manager")) new ServersManager().modoServersGui((Player) sender);
        return true;
    }
}
