package hu.darkcode.dev.luckyLC.Listeners;

import hu.darkcode.dev.luckyLC.Utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class ChatEvent implements Listener {

    private final JavaPlugin plugin;

    public ChatEvent(JavaPlugin plugin) {
         this.plugin = plugin;
    }


    @EventHandler
    public void PlayerChatEvent(PlayerChatEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        p.sendMessage(Color.t(plugin.getConfig().getString("chat.deny-message")));
    }
}
