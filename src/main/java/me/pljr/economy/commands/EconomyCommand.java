package me.pljr.economy.commands;

import me.pljr.economy.config.Lang;
import me.pljr.pljrapispigot.utils.CommandUtil;
import me.pljr.pljrapispigot.utils.PlayerUtil;
import me.pljr.pljrapispigot.utils.VaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class EconomyCommand extends CommandUtil {

    public EconomyCommand(){
        super("economy", "economy.use");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args){
        // /economy
        if (args.length == 0){
            sendMessage(player, Lang.ECONOMY_SUCCESS.get().replace("{money}", VaultUtil.getBalance(player)+""));
            return;
        }

        else if (args.length == 1){
            // /economy help
            if (args[0].equalsIgnoreCase("help")){
                if (!checkPerm(player, "economy.help")) return;
                sendMessage(player, Lang.HELP.get());
                return;
            }
        }

        else if (args.length == 2){
            // /economy balance <player>
            if (args[0].equalsIgnoreCase("balance")){
                if (!checkPerm(player, "economy.balance")) return;
                sendMessage(player, Lang.ECONOMY_BALANCE_SUCCESS.get().replace("{player}", args[1]).replace("{money}", VaultUtil.getBalance(args[1])+""));
                return;
            }
        }

        else if (args.length == 3){
            // /economy send <player> <amount>
            if (args[0].equalsIgnoreCase("send")){
                if (!checkPerm(player, "economy.send")) return;
                if (!checkInt(player, args[2])) return;
                double amount = Integer.parseInt(args[2]);
                double playerBalance = VaultUtil.getBalance(player);
                if (amount > playerBalance){
                    sendMessage(player, Lang.ECONOMY_SEND_FAILURE_TOO_MUCH.get());
                    return;
                }
                VaultUtil.deposit(args[1], amount);
                VaultUtil.withdraw(player, amount);
                sendMessage(player, Lang.ECONOMY_SEND_SUCCESS.get().replace("{money}", amount+"").replace("{player}", args[1]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player requestedPlayer = Bukkit.getPlayer(args[1]);
                    sendMessage(requestedPlayer, Lang.ECONOMY_SEND_SUCCESS_NOTIFY.get().replace("{money}", amount+"").replace("{player}", player.getName()));
                }
                return;
            }
        }

        if (checkPerm(player, "economy.help")){
            sendMessage(player, Lang.HELP.get());
        }
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args){
        if (args.length == 1){
            // /economy help
            if (args[0].equalsIgnoreCase("help")){
                sendMessage(sender, Lang.HELP.get());
                return;
            }
        }

        else if (args.length == 2){
            // /economy balance <player>
            if (args[0].equalsIgnoreCase("balance")){
                sendMessage(sender, Lang.ECONOMY_BALANCE_SUCCESS.get().replace("{player}", args[1]).replace("{money}", VaultUtil.getBalance(args[1])+""));
                return;
            }
        }

        sendMessage(sender, Lang.HELP.get());
    }
}
