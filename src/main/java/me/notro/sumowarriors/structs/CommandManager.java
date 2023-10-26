package me.notro.sumowarriors.structs;

import lombok.NonNull;
import me.notro.sumowarriors.utils.PlayerUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandManager implements CommandExecutor, TabCompleter {

    private final Map<String, CommandManager>
            commands = new HashMap<>();

    protected CommandManager(@NonNull String command) {

        this.commands.put(command, this);
    }

    protected abstract boolean isPlayerCommand();
    protected abstract String getPermission();
    protected abstract String getSyntax();
    protected abstract void executeCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args);
    protected abstract Map<Integer, List<String>> completions();

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @NonNull String[] args) {
        if (isPlayerCommand() && PlayerUtils.notPlayer(sender))
            return false;

        if (PlayerUtils.noPermission(sender, getPermission()))
            return false;

        executeCommand(sender, command, label, args);
        return true;
    }

    @Override
    @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (isPlayerCommand() && PlayerUtils.notPlayer(sender))
            return null;

        if (PlayerUtils.noPermission(sender, getPermission()))
            return null;

        if (completions() == null || !completions().containsKey(args.length))
            return null;

        return completions().get(args.length).stream().toList();
    }
}
