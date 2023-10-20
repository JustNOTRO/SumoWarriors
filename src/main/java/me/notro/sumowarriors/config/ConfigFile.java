package me.notro.sumowarriors.config;

import lombok.NonNull;
import lombok.SneakyThrows;
import me.notro.sumowarriors.SumoWarriors;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

    private final File
            file;
    private final FileConfiguration
            configuration;

    @SneakyThrows({InvalidConfigurationException.class, IOException.class})
    public ConfigFile(@NonNull SumoWarriors plugin, @NonNull String name) {
        this.file = new File(plugin.getDataFolder(),name + ".yml");
        this.configuration = new YamlConfiguration();

        if (!file.exists() && !file.getParentFile().mkdirs()) {
            file.createNewFile();
            file.getParentFile().mkdirs();
            plugin.saveResource(name + ".yml", false);
        }

        configuration.load(file);
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    public void reloadConfig() {
        YamlConfiguration.loadConfiguration(file);
    }

    @SneakyThrows(IOException.class)
    public void saveConfig() {
        configuration.save(file);
    }

    public void copyConfig() {
        configuration.options().copyDefaults(true);
    }

    public String getPath(@NonNull String path) {
        return !configuration.isSet(path) ? "Could not find '" + path + "'" : path;
    }
}
