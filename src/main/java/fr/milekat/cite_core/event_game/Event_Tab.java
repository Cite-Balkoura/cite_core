package fr.milekat.cite_core.event_game;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Event_Tab implements TabCompleter {
    /**
     *      Tab completer pour la commande /event (Custom pour les modos)
     * @return tablist
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (cmd.getName().equalsIgnoreCase("event") && args.length<=1) {
                ArrayList<String> tabEvent = new ArrayList<>(Arrays.asList("box", "help"));
                if (player.hasPermission("modo.command.event.getbox")) {
                    tabEvent.add("getbox");
                }
                if (player.hasPermission("modo.command.event.seebox")) {
                    tabEvent.add("seebox");
                }
                if (player.hasPermission("modo.command.core.event.give")) {
                    tabEvent.add("give");
                }
                final ArrayList<String> completions = new ArrayList<>();
                StringUtil.copyPartialMatches(args[0], tabEvent, completions);
                Collections.sort(completions);
                return completions;
            }
        }
        return null;
    }
}