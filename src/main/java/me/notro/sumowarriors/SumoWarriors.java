package me.notro.sumowarriors;

import lombok.Getter;
import me.notro.sumowarriors.commands.*;
import me.notro.sumowarriors.config.ConfigFile;
import me.notro.sumowarriors.listeners.*;
import me.notro.sumowarriors.managers.*;
import me.notro.sumowarriors.structs.StringReplacer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

@Getter
public final class SumoWarriors extends JavaPlugin {

    private ArenaManager arenaManager;
    private CoinsManager coinsManager;
    private GameManager gameManager;
    private RequestManager requestManager;
    private ScoreboardManager scoreboardManager;
    private ConfigFile sumoFile;

    private final StringReplacer
            stringReplacer = new StringReplacer();

    @Override
    public void onEnable() {
        this.sumoFile = new ConfigFile(this, "sumowarriors");

        loadCommands();
        loadListeners();
        loadManagers();
        loadStringReplaces();
        getLogger().info("has been enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("has been disabled");
    }

    private void loadCommands() {
        Objects.requireNonNull(getCommand("arena")).setExecutor(new ArenaCommand(this));
        Objects.requireNonNull(getCommand("coins")).setExecutor(new CoinsCommand(this));
        Objects.requireNonNull(getCommand("duel")).setExecutor(new DuelCommand(this));
        Objects.requireNonNull(getCommand("lobby")).setExecutor(new LobbyCommand(this));
        Objects.requireNonNull(getCommand("setlobby")).setExecutor(new SetLobbyCommand(this));
        Objects.requireNonNull(getCommand("config")).setExecutor(new ReloadConfigCommand(this));
    }

    private void loadListeners() {
        getServer().getPluginManager().registerEvents(new EntityDamageListener(this), this);
        getServer().getPluginManager().registerEvents(new FoodLevelChangeListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerRespawnListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerSpawnLocationListener(this), this);
    }

    private void loadManagers() {
        this.arenaManager = new ArenaManager(this);
        this.coinsManager = new CoinsManager(this);
        this.gameManager = new GameManager(this);
        this.requestManager = new RequestManager(this);
        this.scoreboardManager = new ScoreboardManager(this);
    }

    private void loadStringReplaces() {
        this.stringReplacer.addReplacer("%PLAYER%", Player::getName);
        this.stringReplacer.addReplacer("%COINS%", player -> String.valueOf(coinsManager.getCoins(player)));
    }
}
