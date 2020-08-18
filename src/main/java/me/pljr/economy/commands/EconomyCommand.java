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

public class EconomyCommand extends CommandUtil implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!checkPerm(sender, "economy.use")) return false;

        // /economy
        if (args.length == 0){
            if (!(sender instanceof Player)){
                sender.sendMessage(CfgLang.lang.get(Lang.NO_CONSOLE));
                return false;
            }
            Player player = (Player) sender;
            player.sendMessage(CfgLang.lang.get(Lang.ECONOMY_SUCCESS).replace("%money", VaultUtil.getBalance(player)+""));
            return true;
        }

        else if (args.length == 1){
            // /economy help
            if (args[0].equalsIgnoreCase("help")){
                if (!checkPerm(sender, "economy.help")) return false;
                sendHelp(sender, CfgLang.help);
                return true;
            }
        }

        else if (args.length == 2){
            // /economy balance <player>
            if (args[0].equalsIgnoreCase("balance")){
                if (!checkPerm(sender, "economy.balance")) return false;
                sender.sendMessage(CfgLang.lang.get(Lang.ECONOMY_BALANCE_SUCCESS).replace("%player", args[1]).replace("%money", VaultUtil.getBalance(args[1])+""));
                return true;
            }
        }

        else if (args.length == 3){
            // /economy send <player> <amount>
            if (args[0].equalsIgnoreCase("send")){
                if (!(sender instanceof Player)){
                    sender.sendMessage(CfgLang.lang.get(Lang.NO_CONSOLE));
                    return false;
                }
                Player player = (Player) sender;
                if (!checkPerm(player, "economy.send")) return false;
                if (!checkInt(player, args[2])) return false;
                double amount = Integer.parseInt(args[2]);
                double playerBalance = VaultUtil.getBalance(player);
                if (amount > playerBalance){
                    player.sendMessage(CfgLang.lang.get(Lang.ECONOMY_SEND_FAILURE_TOO_MUCH));
                    return false;
                }
                VaultUtil.deposit(args[1], amount);
                VaultUtil.withdraw(player, amount);
                player.sendMessage(CfgLang.lang.get(Lang.ECONOMY_SEND_SUCCESS).replace("%money", amount+"").replace("%player", args[1]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player requestedPlayer = Bukkit.getPlayer(args[1]);
                    requestedPlayer.sendMessage(CfgLang.lang.get(Lang.ECONOMY_SEND_SUCCESS_NOTIFY).replace("%money", amount+"").replace("%player", player.getName()));
                }
                return true;
            }
        }

        if (checkPerm(sender, "economy.help")){
            sendHelp(sender, CfgLang.help);
        }
        return false;
    }
}
