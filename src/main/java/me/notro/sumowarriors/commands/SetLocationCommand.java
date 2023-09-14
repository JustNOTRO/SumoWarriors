package me.notro.sumowarriors.commands;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SetLocationCommand implements CommandExecutor {

    private final SumoWarriors plugin;

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (!(sender instanceof Player player)) {
            ChatUtils.sendComponentMessage(sender, ChatUtils.NO_SENDER_EXECUTOR);
            return false;
        }

        final String requiredPermission = "sumowarrios.setlocation";

        if (!player.hasPermission(requiredPermission)) {
            ChatUtils.sendComponentMessage(player, ChatUtils.NO_PERMISSION);
            return false;
        }

        final String syntax = "&c/&7" + label + " '1' | '2'";

        if (args.length < 1) {
            ChatUtils.sendPrefixedMessage(player, syntax);
            return false;
        }

        switch (args[0]) {
            case "1" -> {
                plugin.getSumoFile().getConfig().set("sumo-locations.1", player.getLocation());
                plugin.getSumoFile().saveConfig();

                ChatUtils.sendPrefixedMessage(player, "&aSuccessfully created location &e1&7.");
            }

            case "2" -> {
                plugin.getSumoFile().getConfig().set("sumo-locations.2", player.getLocation());
                plugin.getSumoFile().saveConfig();

                ChatUtils.sendPrefixedMessage(player, "&aSuccessfully created location &e2&7.");
            }

            default -> ChatUtils.sendPrefixedMessage(player, syntax);
        }

        return true;
    }
}
