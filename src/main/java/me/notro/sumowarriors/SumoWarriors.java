package me.notro.sumowarriors;

import lombok.Getter;
import me.notro.sumowarriors.commands.SetLocationCommand;
import me.notro.sumowarriors.managers.RequestManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class SumoWarriors extends JavaPlugin {

    private ConfigFile sumoFile;
    private RequestManager requestManager;

    @Override
    public void onEnable() {
        sumoFile = new ConfigFile(this, "sumowarriors");

        loadCommands();
        loadListeners();
        loadManagers();
        getLogger().info("has been enabled");
    }

    @Override
    public void onDisable() {

        getLogger().info("has been disabled");
    }

    private void loadCommands() {
        getCommand("setlocation").setExecutor(new SetLocationCommand(this));
    }

    private void loadListeners() {

    }

    private void loadManagers() {
        requestManager = new RequestManager();
    }
}
