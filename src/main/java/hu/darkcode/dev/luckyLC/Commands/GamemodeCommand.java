package hu.darkcode.dev.luckyLC.Commands;

import hu.darkcode.dev.luckyLC.Utils.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GamemodeCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public GamemodeCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Color.t("&a&lLOBBY &8| &cCsak játékos használhatja a parancsot!"));
            return true;
        }

        Player p = (Player) sender;

        if (!p.hasPermission("luckylc.gamemode")) {
            p.sendMessage(Color.t("&a&lLOBBY &8| &cNincs jogod a parancs használatához!"));
            return true;
        }

        if (label.equalsIgnoreCase("gms")) {
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage(Color.t("&a&lLOBBY &8| &fJátékmódod megváltoztatva: &aTúlélő"));
            return true;
        }

        if (label.equalsIgnoreCase("gmc")) {
            p.setGameMode(GameMode.CREATIVE);
            p.sendMessage(Color.t("&a&lLOBBY &8| &fJátékmódod megváltoztatva: &aKreatív"));
            return true;
        }

        if (label.equalsIgnoreCase("gma")) {
            p.setGameMode(GameMode.ADVENTURE);
            p.sendMessage(Color.t("&a&lLOBBY &8| &fJátékmódod megváltoztatva: &aKaland"));
            return true;
        }

        if (label.equalsIgnoreCase("gmsp")) {
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(Color.t("&a&lLOBBY &8| &fJátékmódod megváltoztatva: &aMegfigyelő"));
            return true;
        }


        if (args.length == 0) {
            p.sendMessage(Color.t("&a&lLOBBY &8| &cHasználat: &f/gamemode (0|1|2|3)"));
            return true;
        }

        switch (args[0]) {

            case "0":
            case "s":
            case "survival":
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(Color.t("&a&lLOBBY &8| &fJátékmódod megváltoztatva: &aTúlélő"));
                break;

            case "1":
            case "c":
            case "creative":
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(Color.t("&a&lLOBBY &8| &fJátékmódod megváltoztatva: &aKreatív"));
                break;

            case "2":
            case "a":
            case "adventure":
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(Color.t("&a&lLOBBY &8| &fJátékmódod megváltoztatva: &aKaland"));
                break;

            case "3":
            case "sp":
            case "spectator":
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(Color.t("&a&lLOBBY &8| &fJátékmódod megváltoztatva: &aMegfigyelő"));
                break;

            default:
                p.sendMessage(Color.t("&a&lLOBBY &8| &cÉrvénytelen játékmód!"));
                break;
        }



        return true;
    }
}


