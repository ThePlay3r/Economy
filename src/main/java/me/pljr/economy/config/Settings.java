package me.pljr.economy.config;

import lombok.Getter;
import me.pljr.pljrapispigot.managers.ConfigManager;

@Getter
public class Settings {
    private final static String PATH = "settings";

    private static Settings instance = null;
    public static Settings get(){
        return instance;
    }

    private final double defaultBalance;

    public Settings(ConfigManager config){
        instance = this;

        defaultBalance = config.getDouble(PATH+".default-balance");
    }
}
