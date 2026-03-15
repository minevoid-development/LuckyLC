package hu.darkcode.dev.luckyLC.Commands;

import hu.darkcode.dev.luckyLC.Utils.Color;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetLobbyCommand implements CommandExecutor {


    private JavaPlugin plugin;

    public SetLobbyCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,Command cmd,String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(Color.t("&a&lLOBBY &8| &cCsak játékos használhatja a parancsot!"));
            return true;
        }

        Player p = (Player) sender;
        Location loc = p.getLocation();

        if(!p.hasPermission("luckylc.setlobby")) {
            p.sendMessage(Color.t("&a&lLOBBY &8| &cNincs jogod a parancs használatához!"));
            return true;
        }

        this.plugin.getConfig().set("lobby.world", loc.getWorld().getName());
        this.plugin.getConfig().set("lobby.x", loc.getX());
        this.plugin.getConfig().set("lobby.y", loc.getY());
        this.plugin.getConfig().set("lobby.z", loc.getZ());
        this.plugin.getConfig().set("lobby.yaw", loc.getYaw());
        this.plugin.getConfig().set("lobby.pitch", loc.getPitch());
        this.plugin.saveConfig();
        p.sendMessage(Color.t(this.plugin.getConfig().getString("lobby.success")));
        return true;
    }
}
