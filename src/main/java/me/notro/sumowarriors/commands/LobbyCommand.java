package me.notro.sumowarriors.commands;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.utils.ChatUtils;
import me.notro.sumowarriors.utils.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class LobbyCommand implements CommandExecutor {

    private final SumoWarriors
            plugin;

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (PlayerUtils.notPlayer(sender))
            return false;

        Player player = PlayerUtils.getPlayer(sender);

        if (PlayerUtils.noPermission(player, "sumowarriors.duel"))
            return false;

        String syntax = "&7/&clobby";

        if (args.length > 0) {
            ChatUtils.sendPrefixedMessage(player, syntax);
            return false;
        }

        Location lobbyLocation = plugin.getSumoFile().getConfig().getLocation("sumo-warriors.locations.lobby");
        if (lobbyLocation == null) {
            ChatUtils.sendPrefixedMessage(player, "&cLobby location is not set&7.");
            return false;
        }

        player.teleport(lobbyLocation);
        return true;
    }
}
