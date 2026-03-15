package hu.darkcode.dev.luckyLC.Items;

import hu.darkcode.dev.luckyLC.Utils.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class InformationBook implements Listener {

    private final JavaPlugin plugin;

    public InformationBook(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        Action action = e.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = p.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            return;
        }

        String materialName = plugin.getConfig().getString("items.infobook.material");
        Material material = Material.matchMaterial(materialName);

        if (material == null) {
            return;
        }

        String name = Color.t(plugin.getConfig().getString("items.infobook.name"));

        if (item.getType() != material) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) {
            return;
        }

        if (!meta.getDisplayName().equals(name)) {
            return;
        }

        List<String> messages = plugin.getConfig().getStringList("items.infobook.message");

        for (String msg : messages) {
            p.sendMessage(Color.t(msg));
        }
    }

    public void give(Player p) {

        Material material = Material.matchMaterial(plugin.getConfig().getString("items.infobook.material"));
        if(material == null) return;

        int slot = plugin.getConfig().getInt("items.infobook.slot");

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if(meta != null) {
            meta.setDisplayName(Color.t(plugin.getConfig().getString( "items.infobook.name")));
            meta.setLore(Color.tl(plugin.getConfig().getStringList("items.infobook.lore")));
            meta.addEnchant(Enchantment.UNBREAKING, 5, true);
            item.setItemMeta(meta);
        }

        p.getInventory().setItem(slot, item);
    }

}