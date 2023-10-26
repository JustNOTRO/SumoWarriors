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

public class SetLobbyCommand extends CommandManager {

    private final SumoWarriors
            plugin;

    public SetLobbyCommand(@NonNull SumoWarriors plugin) {
        super("setlobby");
        this.plugin = plugin;
    }

    @Override
    protected boolean isPlayerCommand() {
        return true;
    }

    @Override
    protected String getPermission() {
        return "sumowarriors.setlobby";
    }

    @Override
    protected String getSyntax() {
        return "/setlobby";
    }

    @Override
    protected void executeCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        Player player = (Player) sender;

        if (args.length != 0) {
            ChatUtils.sendPrefixedMessage(player, getSyntax());
            return;
        }

        plugin.getSumoFile()
                .getConfig()
                .set("sumo-warriors.locations.lobby", player.getLocation());

        plugin.getSumoFile().saveConfig();
        ChatUtils.sendPrefixedMessage(player, "&7Lobby location has been set.");
    }

    @Override
    protected Map<Integer, List<String>> completions() {
        return null;
    }
}
