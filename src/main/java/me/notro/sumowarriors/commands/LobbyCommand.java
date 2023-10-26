package me.notro.sumowarriors.commands;

import lombok.NonNull;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.structs.CommandManager;
import me.notro.sumowarriors.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class LobbyCommand extends CommandManager {

    private final SumoWarriors
            plugin;

    public LobbyCommand(@NonNull SumoWarriors plugin) {
        super("lobby");
        this.plugin = plugin;
    }

    @Override
    protected boolean isPlayerCommand() {
        return true;
    }

    @Override
    protected String getPermission() {
        return "sumowarriors.lobby";
    }

    @Override
    protected String getSyntax() {
        return "/lobby";
    }

    @Override
    protected void executeCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        Player player = (Player) sender;

        if (args.length != 0) {
            ChatUtils.sendPrefixedMessage(player, getSyntax());
            return;
        }

        Location lobbyLocation = plugin.getSumoFile()
                .getConfig()
                .getLocation("sumo-warriors.locations.lobby");

        if (lobbyLocation == null) {
            ChatUtils.sendPrefixedMessage(player, "&cLobby location is not set&7.");
            return;
        }

        player.teleport(lobbyLocation);
    }

    @Override
    protected Map<Integer, List<String>> completions() {
        return null;
    }
}
