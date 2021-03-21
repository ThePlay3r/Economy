package me.pljr.economy.listeners;

import lombok.AllArgsConstructor;
import me.pljr.economy.config.Lang;
import me.pljr.economy.config.Settings;
import me.pljr.economy.managers.PlayerManager;
import me.pljr.pljrapispigot.utils.ChatUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class PlayerDeathListener implements Listener {
    private final static Settings SETTINGS = Settings.get();

    private final PlayerManager playerManager;

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player victim = event.getEntity();
        playerManager.getPlayer(victim.getUniqueId(), economyVictim -> {
            double victimBalance = economyVictim.getMoney();
            if (victim.getKiller() == null){
                double lose = (victimBalance/100) * SETTINGS.getDeathLose();
                ChatUtil.sendMessage(victim, Lang.DEATH_MONEY_REMOVE.get().replace("{money}", lose+""));
                economyVictim.setMoney(victimBalance-lose);
                playerManager.setPlayer(victim.getUniqueId(), economyVictim);
            } else {
                Player killer = victim.getKiller();
                playerManager.getPlayer(killer.getUniqueId(), economyKiller -> {
                    double lose = (victimBalance/100) * SETTINGS.getDeathLosePlayer();
                    ChatUtil.sendMessage(victim, Lang.DEATH_MONEY_REMOVE.get().replace("{money}", lose+""));
                    ChatUtil.sendMessage(killer, Lang.DEATH_MONEY_GAIN.get()
                            .replace("{player}", victim.getName())
                            .replace("{money}", lose+""));
                    economyKiller.setMoney(economyKiller.getMoney()+lose);
                    economyVictim.setMoney(victimBalance-lose);
                    playerManager.setPlayer(killer.getUniqueId(), economyKiller);
                    playerManager.setPlayer(victim.getUniqueId(), economyVictim);
                });
            }
        });
    }
}
