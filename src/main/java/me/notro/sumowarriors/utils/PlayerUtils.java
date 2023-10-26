package me.notro.sumowarriors.utils;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerUtils {

    public static boolean notPlayer(@NonNull CommandSender sender) {
        if (sender instanceof Player)
            return false;

        ChatUtils.sendComponentMessage(sender, ChatUtils.NO_SENDER_EXECUTOR);
        return true;
    }

    public static boolean noPermission(@NonNull CommandSender sender, @NonNull String permission) {
        if (sender.hasPermission(permission))
            return false;

        ChatUtils.sendComponentMessage(sender, ChatUtils.NO_PERMISSION);
        return true;
    }

    public static boolean notExist(@Nullable Player player, @NonNull CommandSender sender) {
        if (player != null)
            return false;

        ChatUtils.sendComponentMessage(sender, ChatUtils.NO_PLAYER_EXISTENCE);
        return true;
    }

    public static boolean samePlayer(@NonNull Player player, @NonNull Player target) {
        if (player != target)
            return false;

        ChatUtils.sendPrefixedMessage(player, "&cYou cannot send a duel request to yourself&7.");
        return true;
    }

    @Nullable
    public static Player getPlayer(@NonNull String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(name))
                return player;
        }
        return null;
    }

    @Nullable
    public static Player getPlayer(@NonNull UUID uuid) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId().equals(uuid))
                return player;
        }
        return null;
    }
}
