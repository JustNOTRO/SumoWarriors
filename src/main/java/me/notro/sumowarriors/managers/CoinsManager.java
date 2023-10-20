package me.notro.sumowarriors.managers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@RequiredArgsConstructor
public class CoinsManager {

    private final SumoWarriors
            plugin;

    public void addCoins(@NonNull Player player, double amount) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "coins");

        data.set(key, PersistentDataType.DOUBLE, getCoins(player) + amount);
    }

    public void removeCoins(@NonNull Player player, double amount) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "coins");

        data.set(key, PersistentDataType.DOUBLE, getCoins(player) - amount);
    }

    public double getCoins(@NonNull Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "coins");

        return data.getOrDefault(key, PersistentDataType.DOUBLE, 0.0D);
    }
}
