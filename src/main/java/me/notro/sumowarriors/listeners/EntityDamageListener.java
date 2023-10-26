package me.notro.sumowarriors.listeners;

import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

@RequiredArgsConstructor
public class EntityDamageListener implements Listener {

    private final SumoWarriors
            plugin;

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        Game game = plugin.getGameManager().getGame();

        if (game == null ||!plugin.getGameManager().inGame(game)) return;
        if (!(event.getEntity() instanceof Player)) return;

        event.setDamage(0.0D);
    }
}
