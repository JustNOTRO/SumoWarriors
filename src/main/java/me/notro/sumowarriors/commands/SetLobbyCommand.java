package me.notro.sumowarriors.commands;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.utils.ChatUtils;
import me.notro.sumowarriors.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class SetLobbyCommand implements CommandExecutor {

    private final SumoWarriors
            plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (PlayerUtils.notPlayer(sender))
            return false;

        Player player = PlayerUtils.getPlayer(sender);

        if (PlayerUtils.noPermission(player, "sumowarriors.setlobby"))
            return false;

        String syntax = "&7/&csetlobby";

        if (args.length > 0) {
            ChatUtils.sendPrefixedMessage(player, syntax);
            return false;
        }

        setLobbyLocation(player);
        return true;
    }

    private void setLobbyLocation(@NonNull Player player) {
        plugin.getSumoFile().getConfig().set("sumo-warriors.locations.lobby", player.getLocation());
        plugin.getSumoFile().saveConfig();
        ChatUtils.sendPrefixedMessage(player, "&7Lobby location has been set.");
    }
}
