package me.notro.sumowarriors.commands;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.Game;
import me.notro.sumowarriors.models.Request;
import me.notro.sumowarriors.utils.ChatUtils;
import me.notro.sumowarriors.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class DuelCommand implements CommandExecutor {

    private final SumoWarriors
            plugin;

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (PlayerUtils.notPlayer(sender))
            return false;

        Player player = PlayerUtils.getPlayer(sender);

        if (PlayerUtils.noPermission(player, "sumowarriors.duel"))
            return false;

        String syntax = "&7/&cduel <invite/accept/deny> <player>";

        if (args.length < 2) {
            ChatUtils.sendPrefixedMessage(player, syntax);
            return false;
        }

        Player target = PlayerUtils.getPlayer(args[1]);

        if (PlayerUtils.notExist(player, target))
            return false;

        switch (args[0].toLowerCase()) {
            case "invite" -> plugin.getRequestManager().sendRequest(player, target);

            case "accept" -> {
                Request request = plugin.getRequestManager().getRequest();
                if (request == null || !plugin.getRequestManager().hasRequest(request)) {
                    ChatUtils.sendPrefixedMessage(player, "&cYou don't have any requests to accept&7.");
                    return false;
                }

                Game game = plugin.getGameManager().getGame();
                if (game != null && plugin.getGameManager().inGame(game)) {
                    ChatUtils.sendPrefixedMessage(player, "&e" + target.getName() + " &7is currently in game.");
                    plugin.getRequestManager().getCountdownTask().cancel();
                    return false;
                }

                ChatUtils.sendPrefixedMessage(player, "&7You have accepted a duel request from &e" + target.getName() + "&7.");
                ChatUtils.sendPrefixedMessage(target, "&e" + player.getName() + " &7accepted your duel request.");
                plugin.getGameManager().startGame(player, target);
                return true;
            }

            case "deny" -> {
                Request request = plugin.getRequestManager().getRequest();
                if (request == null || !plugin.getRequestManager().hasRequest(request)) {
                    ChatUtils.sendPrefixedMessage(player, "&cYou don't have any requests to deny&7.");
                    return false;
                }

                ChatUtils.sendPrefixedMessage(player, "&cYou have denied a duel request from &e" + target.getName() + "&7.");
                ChatUtils.sendPrefixedMessage(target, "&e" + player.getName() + " &cdenied your request&7.");
                return true;
            }

            default -> ChatUtils.sendPrefixedMessage(player, syntax);
        }
        return true;
    }
}
