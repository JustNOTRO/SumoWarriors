package me.notro.sumowarriors.managers;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.Arena;
import me.notro.sumowarriors.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;

public class ArenaManager {

    private final SumoWarriors
            plugin;

    private final Set<Arena>
            arenas = new HashSet<>();

    @Getter
    private final String
            arenaName = "arena-" + (getArenasCount() + 1);

    public ArenaManager(@NonNull SumoWarriors plugin) {
        this.plugin = plugin;

        loadArenas();
    }

    public void addArena(@NonNull String name) {
        Arena arena = new Arena(plugin, name);
        File sourceFile = Objects.requireNonNull(Bukkit.getWorld("world")).getWorldFolder();
        File destinationFile = new File(Bukkit.getWorldContainer(), name);

        arenas.add(arena);
        copyWorld(sourceFile, destinationFile);

        plugin.getSumoFile()
                .getConfig()
                .set("arenas." + name + ".world", name);

        plugin.getSumoFile()
                .getConfig()
                .set("arenas." + name + ".uuid", arena.getUuid().toString());

        plugin.getSumoFile()
                .getConfig()
                .set("arenas." + name + ".location.1",
                        new Location(
                                Bukkit.getWorld(name),
                                1280.5093995906436D,
                                65.0D,
                                1211.5303050484658D,
                                89.92225F,
                                3.1498647F
                        )
                );

        plugin.getSumoFile()
                .getConfig()
                .set("arenas." + name + ".location.2",
                        new Location(
                                Bukkit.getWorld(name),
                                1272.477565379643D,
                                65.0D,
                                1211.5364677002372D,
                                -90.66302F,
                                -0.1499997F
                        )
                );

        plugin.getSumoFile().saveConfig();
        unloadWorld(name);
    }

    public void removeArena(@NonNull Arena arena) {
        arenas.remove(arena);
        deleteWorld(arena.getName());
        unloadWorld(arena.getName());
    }

    public int getArenasCount() {
        return arenas.size();
    }

    public void loadArenas() {
        ConfigurationSection section = plugin.getSumoFile().getConfig().getConfigurationSection("arenas");

        if (section == null || !section.isConfigurationSection("arenas")) {
            plugin.getSumoFile().getConfig().createSection("arenas");
            return;
        }

        for (String key : section.getKeys(false)) {
            ConfigurationSection arenaSection = plugin.getSumoFile().getConfig().getConfigurationSection("arenas." + key);
            if (arenaSection == null) continue;

            Arena arena = new Arena(plugin, key);
            arenas.add(arena);
        }
    }

    public void setArenaLocation(@NonNull Player player, @NonNull String name, @NonNull String locationName) {
        Arena arena = getArena(name);
        if (arena == null || !plugin.getArenaManager().exists(arena)) {
            ChatUtils.sendPrefixedMessage(player, "&cArena does not exist&7.");
            return;
        }

        switch (locationName) {
            case "1" -> {
                plugin.getSumoFile()
                        .getConfig()
                        .set("arenas." + name + ".location.1", player.getLocation());

                plugin.getSumoFile().saveConfig();
                ChatUtils.sendPrefixedMessage(player, "&eFirst location has been set&7.");
            }

            case "2" -> {
                plugin.getSumoFile()
                        .getConfig()
                        .set("arenas." + name + ".location.2", plugin.getSumoFile().getConfig().getLocation(""));

                plugin.getSumoFile().saveConfig();
                ChatUtils.sendPrefixedMessage(player, "&eSecond location has been set&7.");
            }

            default -> ChatUtils.sendPrefixedMessage(player, "&7/&carena <name> <setlocation> '1' | '2'");
        }
    }

    public boolean exists(@NonNull Arena arena) {
        return arenas.contains(arena);
    }

    @Nullable
    public Arena getArena(@NonNull String name) {
        for (Arena arena : arenas) {
            if (arena.getName().equalsIgnoreCase(name))
                return arena;
        }
        return null;
    }

    public Location getFirstLocation(@NonNull String name) {
        return plugin.getSumoFile()
                .getConfig()
                .getLocation("arenas." + name + ".location.1");
    }

    public Location getSecondLocation(@NonNull String name) {
        return plugin.getSumoFile()
                .getConfig()
                .getLocation("arenas." + name + ".location.2");
    }

    @SneakyThrows(IOException.class)
    private void copyWorld(@NonNull File source, @NonNull File target) {
        List<String> ignore = List.of("uid.dat", "session.dat");
        if (ignore.contains(source.getName())) return;

        if (source.isDirectory()) {
            String[] files = source.list();
            if (files == null) return;

            for (String file : files) {
                if (file == null) continue;

                File sourceFile = new File(source, file);
                File destinationFile = new File(target, file);
                copyWorld(sourceFile, destinationFile);
            }
        } else {
            if (!target.getParentFile().exists())
                target.getParentFile().mkdirs();

            InputStream inputStream = new FileInputStream(source);
            OutputStream outputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = inputStream.read(buffer)) > 0)
                outputStream.write(buffer, 0, length);

            inputStream.close();
            outputStream.close();
        }
    }

    private boolean deleteWorld(@NonNull String name) {
        File destinationFile = Objects.requireNonNull(Bukkit.getWorld(name)).getWorldFolder();
        if (!destinationFile.exists()) return false;

        File[] files = destinationFile.listFiles();
        if (files == null) return false;

        for (File file : files)
            return file.isDirectory() ? deleteWorld(file.getName()) : file.delete();

        return destinationFile.delete();
    }

    private void unloadWorld(@NonNull String name) {
        Bukkit.unloadWorld(name, true);
    }

    public void displayArenas(@NonNull Player player) {
        if (arenas.isEmpty()) {
            ChatUtils.sendPrefixedMessage(player, "&cThere's currently no arenas&7.");
            return;
        }

        ChatUtils.sendPrefixedMessage(player, "&7Existing arenas: ");
        for (Arena arena : arenas) ChatUtils.sendPrefixedMessage(player, "&e" + arena.getName());
    }
}
