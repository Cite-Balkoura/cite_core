package fr.milekat.cite_core.core.events;

import fr.milekat.cite_libs.core.events_register.RedisMessageReceive;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static fr.milekat.cite_core.event_game.Event_Utils.closeEventBox;

public class RedisMessage implements Listener {

    @EventHandler
    public void onRedisReceive(RedisMessageReceive event){
        String[] msg = event.getFullArgs();
        if ("closeeventbox".equals(event.getLabel())) {
            closeEventBox(msg[1]);
        }
    }
}
