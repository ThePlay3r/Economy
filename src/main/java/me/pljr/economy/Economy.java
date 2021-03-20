package me.pljr.economy;

import me.pljr.economy.commands.AEconomyCommand;
import me.pljr.economy.commands.EconomyCommand;
import me.pljr.economy.config.Lang;
import me.pljr.economy.config.Settings;
import me.pljr.economy.handlers.VaultHandler;
import me.pljr.economy.listeners.AsyncPlayerPreLoginListener;
import me.pljr.economy.listeners.PlayerQuitListener;
import me.pljr.economy.managers.PlayerManager;
import me.pljr.economy.managers.QueryManager;
import me.pljr.pljrapispigot.database.DataSource;
import me.pljr.pljrapispigot.events.PLJRApiStartupEvent;
import me.pljr.pljrapispigot.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Economy extends JavaPlugin implements Listener {

    public static Logger log;

    private QueryManager queryManager;
    private PlayerManager playerManager;

    // Files
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        log = getLogger();
        setupConfig();
        setupDatabase();
        setupManagers();
        setupListeners();
        setupCommands();
        getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, new VaultHandler(playerManager), this, ServicePriority.Highest);
        getServer().getPluginManager().registerEvents(this, this);
    }

    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(this, "config.yml");
        new Settings(configManager);
        Lang.load(new ConfigManager(this, "lang.yml"));
    }

    private void setupDatabase(){
        DataSource dataSource = new DataSource(configManager);
        dataSource.initPool("Economy-Pool");
        queryManager = new QueryManager(dataSource);
        queryManager.setupTables();
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.loadPlayer(player.getUniqueId());
        }
    }

    private void setupManagers(){
        playerManager = new PlayerManager(this, queryManager, true);
    }

    private void setupCommands(){
        new EconomyCommand().registerCommand(this);
        new AEconomyCommand().registerCommand(this);
    }

    private void setupListeners(){
        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(playerManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()){
            playerManager.getPlayer(player.getUniqueId(), queryManager::savePlayer);
        }
    }
}
