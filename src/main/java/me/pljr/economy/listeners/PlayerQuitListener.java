package me.pljr.economy.listeners;

import me.pljr.economy.Economy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Economy.getPlayerManager().savePlayer(event.getPlayer().getUniqueId());
    }
}
