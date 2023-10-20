package me.notro.sumowarriors.managers;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.Arena;
import me.notro.sumowarriors.models.Game;
import me.notro.sumowarriors.models.Request;
import me.notro.sumowarriors.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class GameManager {

    private final SumoWarriors
            plugin;
    private final Set<Game>
            games = new HashSet<>();

    @Getter
    private Game game;

    @Getter
    private BukkitTask startTask;

    public void startGame(@NonNull Player requester, @NonNull Player target) {
        Request request = plugin.getRequestManager().getRequest();
        plugin.getRequestManager().removeRequest(request);
        plugin.getRequestManager().getCountdownTask().cancel();

        startTask = new BukkitRunnable() {
            int counter = 3;
            @Override
            public void run() {
                if (counter >= 1) {
                    requester.showTitle(Title.title(Component.text("Starting in: " + counter), Component.text("")));
                    target.showTitle(Title.title(Component.text("Starting in: " + counter), Component.text("")));
                    counter--;
                } else {
                    cancel();
                    addGame(requester, target);
                    sendToGame(requester, target);
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void sendToGame(@NonNull Player requester, @NonNull Player target) {
        String arenaName = plugin.getArenaManager().getArenaName();

        plugin.getArenaManager().addArena(arenaName);
        Location firstLocation = plugin.getArenaManager().getFirstLocation(arenaName);
        Location secondLocation = plugin.getArenaManager().getSecondLocation(arenaName);

        if (firstLocation == null || secondLocation == null) {
            removeGame(game);
            ChatUtils.sendPrefixedMessage(requester, "&cAn error occurred while trying send to game, please contact an administrator&7.");
            return;
        }

        if (!firstLocation.getWorld().equals(requester.getWorld()) || !secondLocation.getWorld().equals(target.getWorld())) {
            firstLocation.setWorld(requester.getWorld());
            secondLocation.setWorld(target.getWorld());
        }

        requester.setGameMode(GameMode.ADVENTURE);
        target.setGameMode(GameMode.ADVENTURE);
        requester.teleport(firstLocation);
        target.teleport(secondLocation);
    }

    public void endGame(@NonNull Player requester, @NonNull Player target) {
        Arena arena = plugin.getArenaManager().getArena(plugin.getArenaManager().getArenaName());
        if (arena == null || !plugin.getArenaManager().exists(arena)) return;

        if (inGame(game) && requester.isInWater()) {
            plugin.getCoinsManager().addCoins(target, 20.0D);
            removeGame(game);
            sendToLobby(requester, target);
            target.showTitle(Title.title(Component.text("VICTORY ROYAL").color(NamedTextColor.GOLD), Component.text("")));
            ChatUtils.sendPrefixedMessage(target, "&7You have received 20.0 coins for playing &eSumo-Warriors!");
            return;
        }

        plugin.getCoinsManager().addCoins(requester, 20.0D);
        removeGame(game);
        sendToLobby(requester, target);
        requester.showTitle(Title.title(Component.text("VICTORY ROYAL").color(NamedTextColor.GOLD), Component.text("")));
        ChatUtils.sendPrefixedMessage(requester, "&7You have received 20.0 coins for playing &eSumo-Warriors!");
    }

    public void sendToLobby(@NonNull Player requester, @NonNull Player target) {
        ChatUtils.sendPrefixedMessage(requester, "&aReturning to lobby...");
        ChatUtils.sendPrefixedMessage(target, "&aReturning to lobby...");

        Location lobbyLocation = plugin.getSumoFile().getConfig().getLocation("sumo-warriors.locations.lobby");
        if (lobbyLocation == null) {
            ChatUtils.sendPrefixedMessage(requester, "&cAn error occurred while trying send to lobby, please contact an administrator&7.");
            return;
        }

        new BukkitRunnable() {
            int counter = 1;
            @Override
            public void run() {
                if (counter <= 5) counter++;
                else {
                    cancel();
                    Game game = getGame();
                    if (game == null || !inGame(game)) {
                        requester.teleport(lobbyLocation);
                        target.teleport(lobbyLocation);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public void addGame(@NonNull Player requester, @NonNull Player target) {
        game = new Game(requester, target);
        games.add(game);
    }

    public void removeGame(@NonNull Game game) {
        games.remove(game);
    }

    public boolean inGame(@NonNull Game game) {
        return games.contains(game);
    }
}
