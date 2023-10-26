package me.notro.sumowarriors.commands;

import lombok.NonNull;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.structs.CommandManager;
import me.notro.sumowarriors.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class ArenaCommand extends CommandManager {

    private final SumoWarriors
            plugin;

    public ArenaCommand(@NonNull SumoWarriors plugin) {
        super("arena");
        this.plugin = plugin;
    }

    @Override
    protected boolean isPlayerCommand() {
        return true;
    }

    @Override
    protected String getPermission() {
        return "sumowarriors.setlocation";
    }

    @Override
    protected String getSyntax() {
        return "&7/&carena <list | setlocation <name> <1 | 2>";
    }

    @Override
    protected void executeCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        Player player = (Player) sender;

        if (args.length == 1 && args[0].equalsIgnoreCase("list"))
            plugin.getArenaManager()
                    .displayArenas(player);
        else if (args.length == 3 && args[0].equalsIgnoreCase("setlocation")) {
            String arenaName = args[1];
            String locationName = args[2];

            plugin.getArenaManager()
                    .setArenaLocation(player, arenaName, locationName);
        } else
            ChatUtils.sendPrefixedMessage(player, getSyntax());
    }

    @Override
    protected Map<Integer, List<String>> completions() {
        return Map.of(1, List.of("list", "setlocation"));
    }
}
