package me.notro.sumowarriors.listeners;

import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

@RequiredArgsConstructor
public class PlayerSpawnLocationListener implements Listener {

    private final SumoWarriors
            plugin;

    @EventHandler
    public void onPlayerSpawn(PlayerSpawnLocationEvent event) {
        Location lobbyLocation = plugin.getSumoFile()
                .getConfig()
                .getLocation("sumo-warriors.locations.lobby");

        if (lobbyLocation == null) return;
        event.setSpawnLocation(lobbyLocation);
    }
}
