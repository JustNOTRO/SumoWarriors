package me.notro.sumowarriors.listeners;

import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

@RequiredArgsConstructor
public class PlayerRespawnListener implements Listener {

    private final SumoWarriors
            plugin;

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location lobbyLocation = plugin.getSumoFile()
                .getConfig()
                .getLocation("sumo-warriors.locations.lobby");

        if (lobbyLocation == null) return;
        player.teleport(lobbyLocation);
    }
}
