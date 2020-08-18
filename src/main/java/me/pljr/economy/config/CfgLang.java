package me.pljr.economy.config;

import me.pljr.economy.enums.Lang;
import me.pljr.pljrapi.managers.ConfigManager;

import java.util.HashMap;
import java.util.List;

public class CfgLang {
    public static List<String> help;
    public static List<String> adminHelp;
    public static HashMap<Lang, String> lang = new HashMap<>();

    public static void load(ConfigManager config){
        CfgLang.help = config.getStringList("help");
        CfgLang.adminHelp = config.getStringList("admin-help");
        for (Lang lang : Lang.values()){
            CfgLang.lang.put(lang, config.getString("lang." + lang.toString()));
        }
    }
}
