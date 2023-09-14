package me.notro.sumowarriors;

import lombok.NonNull;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

    private final @NonNull File file;
    private final @NonNull FileConfiguration configuration;

    public ConfigFile(@NonNull SumoWarriors plugin, @NonNull String name) {
        this.file = new File(plugin.getDataFolder(),name + ".yml");
        this.configuration = new YamlConfiguration();

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(name + ".yml", false);
        }

        try {
            configuration.load(file);
        } catch (InvalidConfigurationException | IOException exception) {
            throw new RuntimeException("Could not load config");
        }
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    public void reloadConfig() {
        YamlConfiguration.loadConfiguration(file);
    }

    public void saveConfig() {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            throw new RuntimeException("Could not save config");
        }
    }

    public String getPath(@NonNull String path) {
        if (!configuration.isSet(path)) return "Could not find " + path;

        return path;
    }
}
