package me.pljr.economy.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.pljr.economy.config.Settings;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class EconomyPlayer {
    private static final Settings SETTINGS = Settings.get();

    private final UUID uniqueId;
    private double money;

    public EconomyPlayer(UUID uniqueId){
        this.uniqueId = uniqueId;
        this.money = SETTINGS.getDefaultBalance();
    }
}
