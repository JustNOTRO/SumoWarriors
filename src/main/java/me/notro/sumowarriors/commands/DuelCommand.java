package me.notro.sumowarriors.commands;

import lombok.NonNull;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.Game;
import me.notro.sumowarriors.models.Request;
import me.notro.sumowarriors.structs.CommandManager;
import me.notro.sumowarriors.utils.ChatUtils;
import me.notro.sumowarriors.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class DuelCommand extends CommandManager {

    private final SumoWarriors
            plugin;

    public DuelCommand(@NonNull SumoWarriors plugin) {
        super("duel");
        this.plugin = plugin;
    }

    @Override
    protected boolean isPlayerCommand() {
        return true;
    }

    @Override
    protected String getPermission() {
        return "sumowarriors.duel";
    }

    @Override
    protected String getSyntax() {
        return "&7/&cduel <invite/accept/deny> <player>";
    }

    @Override
    protected void executeCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        Player player = (Player) sender;

        if (args.length != 2) {
            ChatUtils.sendPrefixedMessage(player, getSyntax());
            return;
        }

        String playerName = args[1];
        Player target = PlayerUtils.getPlayer(playerName);
        if (PlayerUtils.notExist(target, player))
            return;

        switch (args[0]) {
            case "invite" -> plugin.getRequestManager().sendRequest(player, target);

            case "accept" -> {
                Request request = plugin.getRequestManager().getRequest();

                if (request == null || !plugin.getRequestManager().hasRequest(request)) {
                    ChatUtils.sendPrefixedMessage(player, "&cYou don't have any requests to accept&7.");
                    return;
                }

                Game game = plugin.getGameManager().getGame();

                if (game != null && plugin.getGameManager().inGame(game)) {
                    ChatUtils.sendPrefixedMessage(player, "&e" + target.getName() + " &7is currently in game.");
                    plugin.getRequestManager().getCountdownTask().cancel();
                    return;
                }

                ChatUtils.sendPrefixedMessage(player,
                        "&7You have accepted a duel request from &e" + target.getName() + "&7."
                );

                ChatUtils.sendPrefixedMessage(target,
                        "&e" + player.getName() + " &7accepted your duel request."
                );

                plugin.getGameManager().startGame(player, target);
            }

            case "deny" -> {
                Request request = plugin.getRequestManager().getRequest();

                if (request == null || !plugin.getRequestManager().hasRequest(request)) {
                    ChatUtils.sendPrefixedMessage(player, "&cYou don't have any requests to deny&7.");
                    return;
                }

                Game game = plugin.getGameManager().getGame();

                if (game != null && plugin.getGameManager().inGame(game)) {
                    ChatUtils.sendPrefixedMessage(player, "&e" + target.getName() + " &7is currently in game.");
                    return;
                }

                ChatUtils.sendPrefixedMessage(player,
                        "&cYou have denied a duel request from &e" + target.getName() + "&7."
                );

                ChatUtils.sendPrefixedMessage(target,
                        "&e" + player.getName() + " &cdenied your request&7."
                );
            }
        }
    }

    @Override
    protected Map<Integer, List<String>> completions() {
        return Map.of(1, List.of("invite", "accept", "deny"));
    }
}
