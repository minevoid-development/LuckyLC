package hu.darkcode.dev.luckyLC.Items;

import hu.darkcode.dev.luckyLC.Main;
import hu.darkcode.dev.luckyLC.Utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ServerSelector implements Listener {

    private final JavaPlugin plugin;

    public ServerSelector(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void give(Player p) {
        String path = "items.serverselector.";
        Material material = Material.matchMaterial(plugin.getConfig().getString(path + "material"));
        if (material == null) return;
        int slot = plugin.getConfig().getInt(path + "slot");
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            String rawName = plugin.getConfig().getString(path + "name");
            if(rawName != null) meta.setDisplayName(Color.t(rawName));
            List<String> rawLore = plugin.getConfig().getStringList(path + "lore");
            if(rawLore != null) meta.setLore(Color.tl(rawLore));
            meta.addEnchant(Enchantment.UNBREAKING, 5, true);
            item.setItemMeta(meta);
        }
        p.getInventory().setItem(slot, item);
    }

    @EventHandler
    public void onPlayerUseSelector(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        ItemStack item = p.getInventory().getItemInMainHand();
        if(item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
        String rawName = plugin.getConfig().getString("items.serverselector.name");
        if(rawName == null) return;
        String selectorName = Color.t(rawName);
        if(item.getItemMeta().getDisplayName().equals(selectorName)) {
            openGUI(p);
            e.setCancelled(true);
        }
    }

    public void openGUI(Player p) {
        FileConfiguration ssConfig = ((Main) plugin).getServerSelectorConfig();
        String title = Color.t(ssConfig.getString("gui.title"));
        int size = ssConfig.getInt("gui.size");
        Inventory inv = Bukkit.createInventory(null, size, title);
        List<Integer> corners = ssConfig.getIntegerList("decor.corners");
        Material decorMat = Material.matchMaterial(ssConfig.getString("decor.material"));
        if (decorMat != null) {
            ItemStack decor = new ItemStack(decorMat);
            ItemMeta dMeta = decor.getItemMeta();
            if (dMeta != null) dMeta.setDisplayName(" ");
            decor.setItemMeta(dMeta);
            for (int slot : corners) if (slot >= 0 && slot < size) inv.setItem(slot, decor);
        }
        Set<String> keys = ssConfig.getConfigurationSection("servers").getKeys(false);
        for (String key : keys) {
            String path = "servers." + key + ".";
            Material mat = Material.matchMaterial(ssConfig.getString(path + "material"));
            int slot = ssConfig.getInt(path + "slot");
            String name = Color.t(ssConfig.getString(path + "name"));
            List<String> lore = Color.tl(ssConfig.getStringList(path + "lore"));
            if (mat != null) {
                ItemStack item = new ItemStack(mat);
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(name);
                    meta.setLore(lore);
                    meta.addEnchant(Enchantment.UNBREAKING, 5, true);
                    item.setItemMeta(meta);
                }
                if (slot >= 0 && slot < size) inv.setItem(slot, item);
            }
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        FileConfiguration ssConfig = ((Main) plugin).getServerSelectorConfig();
        String title = Color.t(ssConfig.getString("gui.title"));
        if (!e.getView().getTitle().equals(title)) return;
        e.setCancelled(true);
        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
        Set<String> keys = ssConfig.getConfigurationSection("servers").getKeys(false);
        for (String key : keys) {
            String path = "servers." + key + ".";
            String server = ssConfig.getString(path + "server");
            String name = Color.t(ssConfig.getString(path + "name"));
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(name) && server != null) {                connectToServer(p, server);
                p.closeInventory();
            }
        }
    }

    private void connectToServer(Player player, String serverName) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(serverName);
            player.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}