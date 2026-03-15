package hu.darkcode.dev.luckyLC.Listeners;

import hu.darkcode.dev.luckyLC.Utils.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class BlockEvents implements Listener {

    private JavaPlugin plugin;

    public BlockEvents(JavaPlugin plugin) {
      this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        p.sendMessage(Color.t(plugin.getConfig().getString("message.deny-message")));
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);
        p.sendMessage(Color.t(plugin.getConfig().getString("message.deny-message")));
    }

}
