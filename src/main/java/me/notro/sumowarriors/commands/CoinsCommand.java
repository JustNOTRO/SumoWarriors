package me.notro.sumowarriors.commands;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.utils.ChatUtils;
import me.notro.sumowarriors.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class CoinsCommand implements CommandExecutor {

    private final SumoWarriors
            plugin;

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (PlayerUtils.notPlayer(sender))
            return false;

        Player player = PlayerUtils.getPlayer(sender);

        if (PlayerUtils.noPermission(player, "sumowarriors.coins"))
            return false;

        String syntax = "&7/&ccoins <player>";

        if (args.length == 0) {
            ChatUtils.sendPrefixedMessage(player, "&7Coins: &6" + plugin.getCoinsManager().getCoins(player));
            return true;
        } else if (args.length > 1) {
            ChatUtils.sendPrefixedMessage(player, syntax);
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (PlayerUtils.notExist(player, target))
            return false;

        ChatUtils.sendPrefixedMessage(player, "&e" + target.getName() + "&7's Coins: &6" + plugin.getCoinsManager().getCoins(target));
        return true;
    }
}
