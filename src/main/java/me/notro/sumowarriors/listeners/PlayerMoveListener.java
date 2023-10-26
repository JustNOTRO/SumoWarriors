package me.notro.sumowarriors.listeners;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.Game;
import org.bukkit.GameMode;
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

        Player requester = game.getRequester();
        Player target = game.getTarget();

        setGameRequirements(requester, target);
        plugin.getGameManager().endGame(requester, target);
    }

    private void setGameRequirements(@NonNull Player player, @NonNull Player target) {
        setGameModeAdventure(player, target);
        setFoodLevel(player, target);
        healPlayers(player, target);
    }

    private void setGameModeAdventure(@NonNull Player player, @NonNull Player target) {
        if (player.getGameMode() == GameMode.ADVENTURE && target.getGameMode() == GameMode.ADVENTURE)
            return;

        player.setGameMode(GameMode.ADVENTURE);
        target.setGameMode(GameMode.ADVENTURE);
    }

    private void setFoodLevel(@NonNull Player player, @NonNull Player target) {
        if (player.getFoodLevel() == 20 && target.getFoodLevel() == 20)
            return;

        player.setFoodLevel(20);
        target.setFoodLevel(20);
    }

    private void healPlayers(@NonNull Player player, @NonNull Player target) {
        if (player.getHealth() == 20.0D && target.getHealth() == 20.0D)
            return;

        player.setHealth(20.0D);
        target.setHealth(20.0D);
    }
}
