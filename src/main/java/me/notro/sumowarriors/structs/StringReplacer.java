package me.notro.sumowarriors.structs;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class StringReplacer {

    private final Map<String, Function<Player, String>>
            replaces = new HashMap<>();


    public void addReplacer(@NonNull String key, @NonNull Function<Player, String> replacer) {
        this.replaces.put(key, replacer);
    }

    public void replace(@NonNull Player player, @NonNull String text) {
        for (var entry : this.replaces.entrySet()) {
            if (text.contains(entry.getKey())) {
                text = text.replaceAll(
                        entry.getKey(),
                        entry.getValue().apply(player)
                );
            }
        }
    }

    @NonNull
    public String getValue(@NonNull Player player, @NonNull String key) {
        for (var entry : this.replaces.entrySet()) {
            if (key.contains(entry.getKey()))
                key = entry.getValue().apply(player);
        }
        return key;
    }

    @Nullable
    public String getKey(@NonNull String text) {
        for (var entry : this.replaces.keySet()) {
            if (entry.contains(text))
                return entry;
        }
        return null;
    }

    @NonNull
    public List<String> getReplaces() {
        List<String> list = new ArrayList<>();
        for (var entry : this.replaces.entrySet())
            list.add(entry.getKey());

        return list;
    }

    public boolean containsKey(@NonNull String key) {
        for (var entry : this.replaces.keySet()) {
            if (key.contains(entry))
                return true;
        }
        return false;
    }
}
