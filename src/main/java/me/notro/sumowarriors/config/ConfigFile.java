package me.notro.sumowarriors.config;

import lombok.NonNull;
import lombok.SneakyThrows;
import me.notro.sumowarriors.SumoWarriors;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFile {

    private final File
            file;
    private final FileConfiguration
            configuration;

    @SneakyThrows(IOException.class)
    public ConfigFile(@NonNull SumoWarriors plugin, @NonNull String name) {
        this.file = new File(plugin.getDataFolder(),name + ".yml");
        this.configuration = new YamlConfiguration();

        if (!this.file.exists() && !this.file.getParentFile().exists()) {
            this.file.createNewFile();
            plugin.saveResource(name + ".yml", false);
        }

        YamlConfiguration.loadConfiguration(this.file);
        reloadConfig();
    }

    public FileConfiguration getConfig() {
        return this.configuration;
    }

    @SneakyThrows
    public void reloadConfig() {
        this.configuration.load(file);
    }

    @SneakyThrows(IOException.class)
    public void saveConfig() {
        this.configuration.save(this.file);
    }

    public void copyConfig() {
        this.configuration.options().copyDefaults(true);
    }

    public String getPath(@NonNull String path) {
        return !this.configuration.isSet(path) ? "Could not find '" + path + "'" : path;
    }
}
