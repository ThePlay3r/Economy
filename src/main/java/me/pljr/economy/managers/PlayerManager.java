package me.pljr.economy.managers;

import me.pljr.economy.Economy;
import me.pljr.economy.objects.CorePlayer;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    private final HashMap<UUID, CorePlayer> players = new HashMap<>();
    private final QueryManager query = Economy.getQueryManager();

    public CorePlayer getCorePlayer(UUID uuid){
        if (players.containsKey(uuid)){
            return players.get(uuid);
        }
        query.loadPlayerSync(uuid);
        return getCorePlayer(uuid);
    }

    public void setCorePlayer(UUID uuid, CorePlayer corePlayer){
        players.put(uuid, corePlayer);
        savePlayer(uuid);
    }

    public void savePlayer(UUID uuid){
        if (!players.containsKey(uuid)) return;
        query.savePlayer(uuid);
    }
}
