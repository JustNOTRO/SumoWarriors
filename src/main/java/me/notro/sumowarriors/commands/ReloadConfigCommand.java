package me.notro.sumowarriors.commands;

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
public class ReloadConfigCommand implements CommandExecutor {

    private final SumoWarriors plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!PlayerUtils.notPlayer(sender))
            return false;

        Player player = PlayerUtils.getPlayer(sender);

        if (PlayerUtils.notExist(sender, player))
            return false;

        if (PlayerUtils.noPermission(player, "sumowarriors.reloadconfig"))
            return false;

        String syntax = "&7/&csumowarriors <reload>";

        if (args.length < 1 || !args[0].equalsIgnoreCase("reload")) {
            ChatUtils.sendPrefixedMessage(player, syntax);
            return false;
        }

        ChatUtils.sendPrefixedMessage(player, "&7Successfully reloaded config.");
        plugin.getSumoFile().reloadConfig();
        return true;
    }
}
