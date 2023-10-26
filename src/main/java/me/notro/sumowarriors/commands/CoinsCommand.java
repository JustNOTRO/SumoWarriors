package me.notro.sumowarriors.commands;

import lombok.NonNull;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.structs.CommandManager;
import me.notro.sumowarriors.utils.ChatUtils;
import me.notro.sumowarriors.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class CoinsCommand extends CommandManager {

    private final SumoWarriors
            plugin;

    public CoinsCommand(@NonNull SumoWarriors plugin) {
        super("coins");
        this.plugin = plugin;
    }


    @Override
    protected boolean isPlayerCommand() {
        return true;
    }

    @Override
    protected String getPermission() {
        return "sumowarriors.coins";
    }

    @Override
    protected String getSyntax() {
        return "&7/&ccoins <player>";
    }

    @Override
    protected void executeCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        Player player = (Player) sender;

        String coins = String.valueOf(plugin.getCoinsManager().getCoins(player));
        if (args.length == 0) {
            ChatUtils.sendPrefixedMessage(player, "&7Coins: &6" + coins);
            return;
        } else if (args.length != 1) {
            ChatUtils.sendPrefixedMessage(player, getSyntax());
            return;
        }

        String playerName = args[0];
        Player target = PlayerUtils.getPlayer(playerName);

        if (PlayerUtils.notExist(target, player))
            return;

        ChatUtils.sendPrefixedMessage(player,
                "&e" + target.getName() + "&7's Coins: &6" + coins
        );
    }

    @Override
    protected Map<Integer, List<String>> completions() {
        return null;
    }
}
