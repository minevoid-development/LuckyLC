package hu.darkcode.dev.luckyLC.Listeners;

import hu.darkcode.dev.luckyLC.Items.InformationBook;
import hu.darkcode.dev.luckyLC.Items.ServerSelector;
import hu.darkcode.dev.luckyLC.Items.TeleportBow;
import hu.darkcode.dev.luckyLC.Utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerEvents implements Listener {

    private JavaPlugin plugin;
    private InformationBook infoBook;
    private TeleportBow teleportBow;
    private ServerSelector serverSelector;

    public PlayerEvents(JavaPlugin plugin) {
        this.plugin = plugin;
        this.infoBook = new InformationBook(plugin);
        this.teleportBow = new TeleportBow(plugin);
        this.serverSelector = new ServerSelector(plugin);
    }


    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
       if(e.getEntity() instanceof Player && e.getDamager() instanceof  Player) {
          e.setCancelled(true);
          Player p = (Player) e.getDamager();
          p.sendMessage(Color.t(plugin.getConfig().getString("message.deny-message")));
       }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
        e.getPlayer().sendMessage(Color.t(plugin.getConfig().getString("message.deny-message")));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        String lobby = plugin.getConfig().getString("lobby.world");
        double x = plugin.getConfig().getDouble("lobby.x");
        double y = plugin.getConfig().getDouble("lobby.y");
        double z = plugin.getConfig().getDouble("lobby.z");
        Location loc = new Location(Bukkit.getWorld(lobby), x, y, z);
        p.teleport(loc);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String lobby = plugin.getConfig().getString("lobby.world");
        double x = plugin.getConfig().getDouble("lobby.x");
        double y = plugin.getConfig().getDouble("lobby.y");
        double z = plugin.getConfig().getDouble("lobby.z");
        float yaw = (float) plugin.getConfig().getDouble("lobby.yaw");
        float pitch = (float) plugin.getConfig().getDouble("lobby.pitch");
        Location loc = new Location(Bukkit.getWorld(lobby), x, y, z, yaw, pitch);
        ItemStack arrow = new ItemStack(Material.ARROW, 1);
        p.teleport(loc);
        p.getInventory().setItem(16, arrow);
        infoBook.give(p);
        teleportBow.give(p);
        serverSelector.give(p);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e )  {
        e.setCancelled(true);
    }
}
