package me.notro.sumowarriors.models;

import lombok.Getter;
import lombok.NonNull;
import me.notro.sumowarriors.SumoWarriors;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;

@Getter
public class DataContainer {

    private final SumoWarriors
            plugin;

    private final PersistentDataContainer
            data;

    private final NamespacedKey
            key;

    public DataContainer(@NonNull SumoWarriors plugin, @NonNull Player player) {
        this.plugin = plugin;
        this.data = player.getPersistentDataContainer();
        this.key = new NamespacedKey(plugin, "coins");
    }
}
