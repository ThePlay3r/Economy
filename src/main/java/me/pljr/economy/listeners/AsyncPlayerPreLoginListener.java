package me.pljr.economy.listeners;

import me.pljr.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event){
        Economy.getQueryManager().loadPlayer(event.getUniqueId());
    }
}
