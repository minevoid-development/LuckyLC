package hu.darkcode.dev.luckyLC.Items;

import hu.darkcode.dev.luckyLC.Utils.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class TeleportBow implements Listener {

    private final JavaPlugin plugin;
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public TeleportBow(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void give(Player p) {
        Material material = Material.matchMaterial(plugin.getConfig().getString("items.teleportbow.material"));
        if(material == null) return;
        int slot = plugin.getConfig().getInt("items.teleportbow.slot");
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if(meta != null) {
            meta.setDisplayName(Color.t(plugin.getConfig().getString("items.teleportbow.name")));
            meta.setLore(Color.tl(plugin.getConfig().getStringList("items.teleportbow.lore")));
            meta.addEnchant(Enchantment.INFINITY, 5, true);
            meta.setUnbreakable(true);
            item.setItemMeta(meta);
        }
        p.getInventory().setItem(slot, item);
    }

    @EventHandler
    public void onArrowShoot(ProjectileLaunchEvent e) {
        if(!(e.getEntity() instanceof Arrow arrow)) return;
        if(!(arrow.getShooter() instanceof Player p)) return;

        ItemStack bow = p.getInventory().getItemInMainHand();
        if(bow == null || bow.getType() != Material.BOW) return;
        String bowName = Color.t(plugin.getConfig().getString("items.teleportbow.name"));
        if(!bow.hasItemMeta() || !bow.getItemMeta().hasDisplayName()) return;
        if(!bow.getItemMeta().getDisplayName().equals(bowName)) return;

        UUID uuid = p.getUniqueId();
        long cooldown = plugin.getConfig().getLong("items.teleportbow.cooldown") * 1000;
        long now = System.currentTimeMillis();
        if(cooldowns.containsKey(uuid) && now - cooldowns.get(uuid) < cooldown) {
            long left = (cooldown - (now - cooldowns.get(uuid))) / 1000;
            p.sendMessage(Color.t("&a&lLOBBY &8| &fVárj még &a" + left + " &fmásodpercet, hogy újra használd!"));
            e.setCancelled(true);
            return;
        }

        cooldowns.put(uuid, now);

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!arrow.isDead() && arrow.isValid()) {
                    Location start = arrow.getLocation();
                    Vector direction = arrow.getVelocity().normalize();
                    RayTraceResult result = arrow.getWorld().rayTraceBlocks(start, direction, 100);
                    Location teleportLoc;

                    if(result != null && result.getHitBlock() != null) {
                        teleportLoc = result.getHitBlock().getLocation().add(0.5, 1, 0.5); // középre + tetejére
                    } else {
                        teleportLoc = arrow.getLocation(); // ha nem talál blokkot
                    }

                    float yaw = p.getLocation().getYaw();
                    float pitch = p.getLocation().getPitch();
                    teleportLoc.setYaw(yaw);
                    teleportLoc.setPitch(pitch);

                    p.teleport(teleportLoc);
                    arrow.remove();
                }
            }
        }.runTaskLater(plugin, 1L);
    }
}