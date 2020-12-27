package me.pljr.economy;

import me.pljr.economy.commands.AEconomyCommand;
import me.pljr.economy.commands.EconomyCommand;
import me.pljr.economy.config.Lang;
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

public final class Economy extends JavaPlugin implements Listener {
    private static Economy instance;
    private static ConfigManager configManager;
    private static QueryManager queryManager;
    private static PlayerManager playerManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, new VaultHandler(), this, ServicePriority.Highest);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onPLJRAPIStartup(PLJRApiStartupEvent event){
        setupConfig();
        setupManagers();
        setupDatabase();
        setupListeners();
        setupCommands();
    }

    private void setupConfig(){
        saveDefaultConfig();
        configManager = new ConfigManager(this, "config.yml");
        Lang.load(configManager);
    }

    private void setupDatabase(){
        DataSource dataSource = DataSource.getFromConfig(configManager);
        queryManager = new QueryManager(dataSource);
        queryManager.setupTables();
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.loadPlayer(player.getUniqueId());
        }
    }

    private void setupManagers(){
        playerManager = new PlayerManager();
    }

    private void setupCommands(){
        getCommand("economy").setExecutor(new EconomyCommand());
        getCommand("aeconomy").setExecutor(new AEconomyCommand());
    }

    private void setupListeners(){
        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    public static QueryManager getQueryManager() {
        return queryManager;
    }

    public static PlayerManager getPlayerManager() {
        return playerManager;
    }

    public static Economy getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        for (Player player : Bukkit.getOnlinePlayers()){
            queryManager.savePlayerSync(player.getUniqueId());
        }
    }
}
