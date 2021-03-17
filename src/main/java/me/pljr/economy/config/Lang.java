package me.pljr.economy.config;

import me.pljr.pljrapispigot.managers.ConfigManager;
import me.pljr.pljrapispigot.utils.FormatUtil;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public enum Lang {
    HELP("" +
            "\n§a§lEconomy Help" +
            "\n" +
            "\n§e/economy §8» §fDisplays your current balance." +
            "\n§e/economy help §8» §fDisplays this message." +
            "\n§e/economy balance <player> §8» §fDisplays current balance of player." +
            "\n§e/economy send <player> <amount> §8» §fSends player amount of money."),
    ADMIN_HELP("" +
            "\n§a§lEconomy Admin-Help" +
            "\n" +
            "\n§e/aeconomy help §8» §fDisplays this message." +
            "\n§e/aeconomy set <player> <amount> §8» §fSets player's balance." +
            "\n§e/aeconomy add <player> <amount> §8» §fAdds money to player's balance." +
            "\n§e/aeconomy remove <player> <amount> §8» §fRemoves money from player's balance."),

    CONSOLE_NAME("Console"),
    NO_POSITIVE_NUMBER("§aEconomy §8» §fThe number must be positive!"),
    ECONOMY_SUCCESS("§aEconomy §8» §fYour balance is: §b${money}§f."),
    ECONOMY_BALANCE_SUCCESS("§aEconomy §8» §fBalance of §b{player} §fis: §b${money}§f."),
    ECONOMY_SEND_SUCCESS("§aEconomy §8» §fSuccessfully send §b{money} §fto §b{player}§f."),
    ECONOMY_SEND_FAILURE_TOO_MUCH("§aEconomy §8» §fYou don't have enough money to do this!"),
    ECONOMY_SEND_SUCCESS_NOTIFY("§aEconomy §8» §fYou received §b{money} §ffrom §b{player}§f."),
    AECONOMY_SET_SUCCESS("§aEconomy §8» §fSuccessfully set balance of §b{player} §fto §b{money}§f."),
    AECONOMY_SET_SUCCESS_NOTIFY("§aEconomy §8» §fYour balance has been set to §b{money} §fby §b{player}§f."),
    AECONOMY_ADD_SUCCESS("§aEconomy §8» §fSuccessfully added §b{money} §fto §b{player}'s §fbalance."),
    AECONOMY_ADD_SUCCESS_NOTIFY("§aEconomy §8» §b{money} §fhas been added to your balance by §b{player}§f."),
    AECONOMY_REMOVE_SUCCESS("§aEconomy §8» §fSuccessfully removed §b{money} §ffrom §b{player}'s §fbalance."),
    AECONOMY_REMOVE_FAILURE_TOO_MUCH("§aEconomy §8» §b{player} §fdoes not have enough money!"),
    AECONOMY_REMOVE_SUCCESS_NOTIFY("§aEconomy §8» §b{money} §fhas been removed from your balance by §b{player}§f."),

    VAULT_WITHDRAW_PLAYER_FAILURE_NO_ACCOUNT("§c{player} does not have an account!"),
    VAULT_WITHDRAW_PLAYER_FAILURE_TOO_MUCH("§c{player} does not have enough money."),
    VAULT_DEPOSIT_PLAYER_FAILURE_NO_ACCOUNT("§c{player} does not have an account!"),
    VAULT_NOT_IMPLEMENTED("§cThis functions is not implemented!");

    private static HashMap<Lang, String> lang;
    private final String defaultValue;

    Lang(String defaultValue){
        this.defaultValue = defaultValue;
    }

    public static void load(ConfigManager config){
        FileConfiguration fileConfig = config.getConfig();
        lang = new HashMap<>();
        for (Lang lang : values()){
            if (!fileConfig.isSet(lang.toString())){
                fileConfig.set(lang.toString(), lang.defaultValue);
            }else{
                Lang.lang.put(lang, config.getString(lang.toString()));
            }
        }
        config.save();
    }

    public String get(){
        return lang.getOrDefault(this, FormatUtil.colorString(defaultValue));
    }
}
