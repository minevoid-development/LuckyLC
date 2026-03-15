package hu.darkcode.dev.luckyLC;

import hu.darkcode.dev.luckyLC.Commands.GamemodeCommand;
import hu.darkcode.dev.luckyLC.Commands.SetLaunchpadCommand;
import hu.darkcode.dev.luckyLC.Commands.SetLobbyCommand;
import hu.darkcode.dev.luckyLC.Items.InformationBook;
import hu.darkcode.dev.luckyLC.Items.ServerSelector;
import hu.darkcode.dev.luckyLC.Items.TeleportBow;
import hu.darkcode.dev.luckyLC.Listeners.BlockEvents;
import hu.darkcode.dev.luckyLC.Listeners.ChatEvent;
import hu.darkcode.dev.luckyLC.Listeners.LaunchpadListener;
import hu.darkcode.dev.luckyLC.Listeners.PlayerEvents;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    private FileConfiguration serverSelectorConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadServerSelectorConfig();
        getLogger().info("§8==============================");
        getLogger().info("§5MINEVOID | §fLOBBYCORE §aelindult!");
        getLogger().info("§aDeveloper: §fDarkCode");
        getLogger().info("§aVerzió: §f" + getDescription().getVersion());
        getLogger().info("§8==============================");
        getServer().getPluginManager().registerEvents(new BlockEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
        getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
        getServer().getPluginManager().registerEvents(new LaunchpadListener(this), this);
        getServer().getPluginManager().registerEvents(new InformationBook(this), this);
        getServer().getPluginManager().registerEvents(new TeleportBow(this), this);
        getServer().getPluginManager().registerEvents(new ServerSelector(this), this);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("setlobby").setExecutor(new SetLobbyCommand(this));
        getCommand("setlaunchpad").setExecutor(new SetLaunchpadCommand(this));
        getCommand("gamemode").setExecutor(new GamemodeCommand(this));
        getCommand("gms").setExecutor(new GamemodeCommand(this));
        getCommand("gmc").setExecutor(new GamemodeCommand(this));
        getCommand("gma").setExecutor(new GamemodeCommand(this));
        getCommand("gmsp").setExecutor(new GamemodeCommand(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("§8==============================");
        getLogger().info("§aMINEVOID | §fLOBBYCORE plugin §cleállt!");
        getLogger().info("§Developer: §fDarkCode");
        getLogger().info("§aVerzió: §f" + getDescription().getVersion());
        getLogger().info("§8==============================");
        saveConfig();
    }

    public void loadServerSelectorConfig() {
        File ssFile = new File(getDataFolder(), "serverselector.yml");
        if(!ssFile.exists()) saveResource("serverselector.yml", false);
        serverSelectorConfig = YamlConfiguration.loadConfiguration(ssFile);
    }

    public FileConfiguration getServerSelectorConfig() {
        return serverSelectorConfig;
    }

}
