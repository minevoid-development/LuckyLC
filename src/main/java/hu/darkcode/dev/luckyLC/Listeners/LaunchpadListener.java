package hu.darkcode.dev.luckyLC.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;


public class LaunchpadListener implements Listener {


    private JavaPlugin plugin;

    public LaunchpadListener (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();

        String lobby = plugin.getConfig().getString("launchpad.world");
        double x = plugin.getConfig().getDouble("launchpad.x");
        double y = plugin.getConfig().getDouble("launchpad.y");
        double z = plugin.getConfig().getDouble("launchpad.z");
        double power = plugin.getConfig().getDouble("launchpad.power");
        double powery = plugin.getConfig().getDouble("launchpad.powery");

        World world = Bukkit.getWorld(lobby);
        if (world == null) return;

        if (!p.getWorld().equals(world)) return;

        if (p.getLocation().getBlockX() == (int) x &&
                p.getLocation().getBlockY() == (int) y &&
                p.getLocation().getBlockZ() == (int) z) {

            Vector direction = p.getLocation().getDirection();
            direction.multiply(power);
            direction.setY(powery);

            p.setVelocity(direction);
        }
    }
}
