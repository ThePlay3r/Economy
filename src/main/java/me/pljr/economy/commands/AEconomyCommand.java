package me.pljr.economy.commands;

import me.pljr.economy.config.CfgLang;
import me.pljr.economy.enums.Lang;
import me.pljr.pljrapi.utils.CommandUtil;
import me.pljr.pljrapi.utils.PlayerUtil;
import me.pljr.pljrapi.utils.VaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AEconomyCommand extends CommandUtil implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!checkPerm(sender, "aeconomy.use")) return false;

        if (args.length == 1){
            // /aeconomy help
            if (args[0].equalsIgnoreCase("help")){
                if (checkPerm(sender, "aeconomy.help")) return false;
                sendHelp(sender, CfgLang.adminHelp);
                return true;
            }
        }

        else if (args.length == 3){
            // /aeconomy set <player> <amount>
            if (args[0].equalsIgnoreCase("set")){
                if (!checkPerm(sender, "aeconomy.set")) return false;
                if (!checkInt(sender, args[2])) return false;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(sender, CfgLang.lang.get(Lang.NO_POSITIVE_NUMBER));
                    return false;
                }
                double playerMoney = VaultUtil.getBalance(args[1]);
                VaultUtil.withdraw(args[1], playerMoney);
                VaultUtil.deposit(args[1], amount);
                sendMessage(sender, CfgLang.lang.get(Lang.AECONOMY_SET_SUCCESS).replace("%player", args[1]).replace("%money", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player player = Bukkit.getPlayer(args[1]);
                    sendMessage(player, CfgLang.lang.get(Lang.AECONOMY_SET_SUCCESS_NOTIFY).replace("%player", sender.getName()));
                }
                return true;
            }

            // /aeconomy add <player> <amount>
            if (args[0].equalsIgnoreCase("add")){
                if (!checkPerm(sender, "aeconomy.add")) return false;
                if (!checkInt(sender, args[2])) return false;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(sender, CfgLang.lang.get(Lang.NO_POSITIVE_NUMBER));
                    return false;
                }
                VaultUtil.deposit(args[1], amount);
                sendMessage(sender, CfgLang.lang.get(Lang.AECONOMY_ADD_SUCCESS)
                        .replace("%player", args[1])
                        .replace("%money", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player player = Bukkit.getPlayer(args[1]);
                    sendMessage(player, CfgLang.lang.get(Lang.AECONOMY_ADD_SUCCESS_NOTIFY)
                            .replace("%player", args[1])
                            .replace("%money", args[2]));
                }
                return true;
            }

            // /aconomy remove <player> <amount>
            if (args[0].equalsIgnoreCase("remove")){
                if (!checkPerm(sender, "aeconomy.remove")) return false;
                if (!checkInt(sender, args[2])) return false;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(sender, CfgLang.lang.get(Lang.NO_POSITIVE_NUMBER));
                    return false;
                }
                double playerMoney = VaultUtil.getBalance(args[1]);
                if ((playerMoney-amount)<0){
                    sendMessage(sender, CfgLang.lang.get(Lang.AECONOMY_REMOVE_FAILURE_TOO_MUCH).replace("%player", args[1]));
                    return false;
                }
                VaultUtil.withdraw(args[1], amount);
                sendMessage(sender, CfgLang.lang.get(Lang.AECONOMY_REMOVE_SUCCESS).replace("%player", args[1]).replace("%money", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player player = Bukkit.getPlayer(args[1]);
                    sendMessage(player, CfgLang.lang.get(Lang.AECONOMY_REMOVE_SUCCESS_NOTIFY).replace("%player", sender.getName()));
                }
                return true;
            }
        }

        if (checkPerm(sender, "aeconomy.help")){
            sendHelp(sender, CfgLang.adminHelp);
        }
        return false;
    }
}
