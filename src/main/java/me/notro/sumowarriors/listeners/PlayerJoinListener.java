package me.notro.sumowarriors.listeners;

import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.managers.ScoreboardManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final SumoWarriors plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ScoreboardManager scoreboardManager = plugin.getScoreboardManager();
        Scoreboard mainScoreboard = plugin.getScoreboardManager().createScoreboard();
        boolean isScoreboardEnabled = plugin.getSumoFile().getConfig().getBoolean("scoreboard.enabled");

        scoreboardManager.generateScoreboard(
                mainScoreboard,
                player,
                "SumoWarriors",
                isScoreboardEnabled
        );
    }
}