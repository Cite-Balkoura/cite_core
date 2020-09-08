package fr.milekat.cite_core.core.commands;

import fr.milekat.cite_core.MainCore;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WebLinks implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("web")) {
            TextComponent output = new TextComponent(MainCore.prefixCmd + "§6[Acceder directement au site]");
            String link = "https://web.cite-balkoura.fr/";
            if (!label.equalsIgnoreCase("web")) {
                link += label;
            }
            output.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
            sender.spigot().sendMessage(output);
        }
        return true;
    }
}