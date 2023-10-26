package me.notro.sumowarriors.managers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.DataContainer;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

@RequiredArgsConstructor
public class CoinsManager {

    private final SumoWarriors
            plugin;
    
    public void addCoins(@NonNull Player player, double amount) {
        DataContainer container = new DataContainer(plugin, player);

        container.getData()
                .set(
                        container.getKey(),
                        PersistentDataType.DOUBLE,
                        getCoins(player) + amount
                );
    }

    public void removeCoins(@NonNull Player player, double amount) {
        DataContainer container = new DataContainer(plugin, player);

        container.getData()
                .set(
                        container.getKey(),
                        PersistentDataType.DOUBLE,
                        getCoins(player) - amount
                );
    }

    public double getCoins(@NonNull Player player) {
        DataContainer container = new DataContainer(plugin, player);

        return container.getData()
                .getOrDefault(
                        container.getKey(),
                        PersistentDataType.DOUBLE,
                        0.0D
                );
    }
}
