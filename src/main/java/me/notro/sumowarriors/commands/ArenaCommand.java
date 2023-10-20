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

@RequiredArgsConstructor
public class ArenaCommand implements CommandExecutor {

    private final SumoWarriors
            plugin;

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (PlayerUtils.notPlayer(sender))
            return false;

        Player player = PlayerUtils.getPlayer(sender);

        if (PlayerUtils.notExist(sender, player))
            return false;

        if (PlayerUtils.noPermission(player, "sumowarriors.setlocation"))
            return false;

        String syntax = "&7/&carena <list | setlocation <name> <1 | 2>";

        if (args.length == 0) {
            ChatUtils.sendPrefixedMessage(player, syntax);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "list" -> {
                if (args.length != 1) {
                    ChatUtils.sendPrefixedMessage(player, syntax);
                    return false;
                }

                plugin.getArenaManager().displayArenas(player);
            }

            case "setlocation" -> {
                if (args.length != 3) {
                    ChatUtils.sendPrefixedMessage(player, syntax);
                    return false;
                }

                plugin.getArenaManager().setArenaLocation(player, args[1], args[2]);
            }
        }
        return true;
    }
}
