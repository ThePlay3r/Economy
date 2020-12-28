package me.pljr.economy.commands;

import me.pljr.economy.config.Lang;
import me.pljr.pljrapispigot.utils.CommandUtil;
import me.pljr.pljrapispigot.utils.PlayerUtil;
import me.pljr.pljrapispigot.utils.VaultUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AEconomyCommand extends CommandUtil {

    public AEconomyCommand(){
        super("aeconomy", "aeconomy.use");
    }

    @Override
    public void onPlayerCommand(Player player, String[] args){
        if (args.length == 1){
            // /aeconomy help
            if (args[0].equalsIgnoreCase("help")){
                if (checkPerm(player, "aeconomy.help")) return;
                sendMessage(player, Lang.ADMIN_HELP);
                return;
            }
        }

        else if (args.length == 3){
            // /aeconomy set <player> <amount>
            if (args[0].equalsIgnoreCase("set")){
                if (!checkPerm(player, "aeconomy.set")) return;
                if (!checkInt(player, args[2])) return;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(player, Lang.NO_POSITIVE_NUMBER.get());
                    return;
                }
                double playerMoney = VaultUtil.getBalance(args[1]);
                VaultUtil.withdraw(args[1], playerMoney);
                VaultUtil.deposit(args[1], amount);
                sendMessage(player, Lang.AECONOMY_SET_SUCCESS.get()
                        .replace("{player}", args[1])
                        .replace("{money}", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player target = Bukkit.getPlayer(args[1]);
                    sendMessage(target, Lang.AECONOMY_SET_SUCCESS_NOTIFY.get()
                            .replace("{player}", player.getName())
                            .replace("{money}", args[2]));
                }
                return;
            }

            // /aeconomy add <player> <amount>
            if (args[0].equalsIgnoreCase("add")){
                if (!checkPerm(player, "aeconomy.add")) return;
                if (!checkInt(player, args[2])) return;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(player, Lang.NO_POSITIVE_NUMBER.get());
                    return;
                }
                VaultUtil.deposit(args[1], amount);
                sendMessage(player, Lang.AECONOMY_ADD_SUCCESS.get()
                        .replace("{player}", args[1])
                        .replace("{money}", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player target = Bukkit.getPlayer(args[1]);
                    sendMessage(target, Lang.AECONOMY_ADD_SUCCESS_NOTIFY.get()
                            .replace("{player}", player.getName())
                            .replace("{money}", args[2]));
                }
                return;
            }

            // /aconomy remove <player> <amount>
            if (args[0].equalsIgnoreCase("remove")){
                if (!checkPerm(player, "aeconomy.remove")) return;
                if (!checkInt(player, args[2])) return;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(player, Lang.NO_POSITIVE_NUMBER.get());
                    return;
                }
                double playerMoney = VaultUtil.getBalance(args[1]);
                if ((playerMoney-amount)<0){
                    sendMessage(player, Lang.AECONOMY_REMOVE_FAILURE_TOO_MUCH.get().replace("{player}", args[1]));
                    return;
                }
                VaultUtil.withdraw(args[1], amount);
                sendMessage(player, Lang.AECONOMY_REMOVE_SUCCESS.get()
                        .replace("{player}", args[1])
                        .replace("{money}", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player target = Bukkit.getPlayer(args[1]);
                    sendMessage(target, Lang.AECONOMY_REMOVE_SUCCESS_NOTIFY.get()
                            .replace("{player}", player.getName())
                            .replace("{money}", args[2]));
                }
                return;
            }
        }

        if (checkPerm(player, "aeconomy.help")){
            sendMessage(player, Lang.ADMIN_HELP);
        }
    }

    @Override
    public void onConsoleCommand(ConsoleCommandSender sender, String[] args){
        if (args.length == 1){
            // /aeconomy help
            if (args[0].equalsIgnoreCase("help")){
                sendMessage(sender, Lang.ADMIN_HELP);
                return;
            }
        }

        else if (args.length == 3){
            // /aeconomy set <player> <amount>
            if (args[0].equalsIgnoreCase("set")){
                if (!checkInt(sender, args[2])) return;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(sender, Lang.NO_POSITIVE_NUMBER.get());
                    return;
                }
                double playerMoney = VaultUtil.getBalance(args[1]);
                VaultUtil.withdraw(args[1], playerMoney);
                VaultUtil.deposit(args[1], amount);
                sendMessage(sender, Lang.AECONOMY_SET_SUCCESS.get()
                        .replace("{player}", args[1])
                        .replace("{money}", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player target = Bukkit.getPlayer(args[1]);
                    sendMessage(target, Lang.AECONOMY_SET_SUCCESS_NOTIFY.get()
                            .replace("{player}", sender.getName())
                            .replace("{money}", args[2]));
                }
                return;
            }

            // /aeconomy add <player> <amount>
            if (args[0].equalsIgnoreCase("add")){
                if (!checkInt(sender, args[2])) return;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(sender, Lang.NO_POSITIVE_NUMBER.get());
                    return;
                }
                VaultUtil.deposit(args[1], amount);
                sendMessage(sender, Lang.AECONOMY_ADD_SUCCESS.get()
                        .replace("{player}", args[1])
                        .replace("{money}", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player target = Bukkit.getPlayer(args[1]);
                    sendMessage(target, Lang.AECONOMY_ADD_SUCCESS_NOTIFY.get()
                            .replace("{player}", sender.getName())
                            .replace("{money}", args[2]));
                }
                return;
            }

            // /aconomy remove <player> <amount>
            if (args[0].equalsIgnoreCase("remove")){
                if (!checkInt(sender, args[2])) return;
                double amount = Integer.parseInt(args[2]);
                if (!(amount > 0)){
                    sendMessage(sender, Lang.NO_POSITIVE_NUMBER.get());
                    return;
                }
                double playerMoney = VaultUtil.getBalance(args[1]);
                if ((playerMoney-amount)<0){
                    sendMessage(sender, Lang.AECONOMY_REMOVE_FAILURE_TOO_MUCH.get().replace("{player}", args[1]));
                    return;
                }
                VaultUtil.withdraw(args[1], amount);
                sendMessage(sender, Lang.AECONOMY_REMOVE_SUCCESS.get()
                        .replace("{player}", args[1])
                        .replace("{money}", args[2]));
                if (PlayerUtil.isPlayer(args[1])){
                    Player target = Bukkit.getPlayer(args[1]);
                    sendMessage(target, Lang.AECONOMY_REMOVE_SUCCESS_NOTIFY.get()
                            .replace("{player}", sender.getName())
                            .replace("{money}", args[2]));
                }
                return;
            }
        }

        sendMessage(sender, Lang.ADMIN_HELP);
    }
}
