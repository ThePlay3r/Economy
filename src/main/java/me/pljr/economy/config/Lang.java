package me.pljr.economy.config;

import me.pljr.pljrapispigot.managers.ConfigManager;

import java.util.HashMap;
import java.util.List;

public enum Lang {
    CONSOLE_NAME,
    NO_POSITIVE_NUMBER,
    ECONOMY_SUCCESS,
    ECONOMY_BALANCE_SUCCESS,
    ECONOMY_SEND_SUCCESS,
    ECONOMY_SEND_FAILURE_TOO_MUCH,
    ECONOMY_SEND_SUCCESS_NOTIFY,
    AECONOMY_SET_SUCCESS,
    AECONOMY_SET_SUCCESS_NOTIFY,
    AECONOMY_ADD_SUCCESS,
    AECONOMY_ADD_SUCCESS_NOTIFY,
    AECONOMY_REMOVE_SUCCESS,
    AECONOMY_REMOVE_FAILURE_TOO_MUCH,
    AECONOMY_REMOVE_SUCCESS_NOTIFY,

    VAULT_WITHDRAW_PLAYER_FAILURE_NO_ACCOUNT,
    VAULT_WITHDRAW_PLAYER_FAILURE_TOO_MUCH,
    VAULT_DEPOSIT_PLAYER_FAILURE_NO_ACCOUNT,
    VAULT_NOT_IMPLEMENTED;

    public static List<String> HELP;
    public static List<String> ADMIN_HELP;

    private static HashMap<Lang, String> lang;

    public static void load(ConfigManager config){
        HELP = config.getStringList("help");
        ADMIN_HELP = config.getStringList("admin-help");
        lang = new HashMap<>();
        for (Lang lang : values()){
            Lang.lang.put(lang, config.getString("lang." + lang.toString()));
        }
    }

    public String get(){
        return lang.get(this);
    }
}
