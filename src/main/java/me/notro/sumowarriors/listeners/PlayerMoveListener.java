package me.notro.sumowarriors.listeners;

import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@RequiredArgsConstructor
public class PlayerMoveListener implements Listener {

    private final SumoWarriors
            plugin;

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Game game = plugin.getGameManager().getGame();
        if (game == null || !plugin.getGameManager().inGame(game)) return;

        Player requester = plugin.getGameManager().getGame().getRequester();
        Player target = plugin.getGameManager().getGame().getTarget();

        plugin.getGameManager().endGame(requester, target);
    }
}
