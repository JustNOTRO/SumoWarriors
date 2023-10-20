package me.notro.sumowarriors.managers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class ScoreboardManager {

    private final SumoWarriors
            plugin;

    @NonNull
    public Scoreboard createScoreboard() {
        return Bukkit.getScoreboardManager()
                .getNewScoreboard();
    }

    public void registerObjective(@NonNull Scoreboard scoreboard, @NonNull String name, @NonNull Criteria criteria, @Nullable Component displayName) {
        Objective objective = scoreboard.registerNewObjective(name, criteria, displayName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void registerScore(@NonNull Scoreboard scoreboard, @NonNull String objectiveName, @NonNull Component scoreName, int scoreNumber) {
        String displayName = LegacyComponentSerializer.legacySection()
                .serialize(scoreName);

        Objects.requireNonNull(scoreboard.getObjective(objectiveName))
                .getScore(displayName)
                .setScore(scoreNumber);
    }

    /*
    scoreboard
        enabled
        title
        lines
            line9
            line8
            line7
            line6
            line5
            line4
            line3
            line2
            line1
            line0
     */

    public void generateScoreboard(@NonNull Scoreboard scoreboard, @NonNull Player player, @NonNull String title, boolean state) {
        if (!state || notExist()) return;

        registerObjective(scoreboard,
                "Main",
                Criteria.DUMMY,
                Component.text(title).color(NamedTextColor.GOLD));

        List<String> lines = plugin.getSumoFile()
                .getConfig()
                .getStringList("scoreboard.lines");

        int numberOfLine = lines.size();

        for (String line : lines) {
            if (plugin.getStringReplacer().containsKey(line)) {
                registerScore(scoreboard,
                        "Main",
                        ChatUtils.fixColor(plugin.getStringReplacer()
                                .getValue(player, line)),
                        numberOfLine
                );
            } else
                registerScore(scoreboard,
                        "Main",
                        ChatUtils.fixColor(line),
                        numberOfLine);

            numberOfLine--;
        }

        player.setScoreboard(scoreboard);
    }

    private boolean notExist() {
        return !plugin.getSumoFile()
                .getConfig()
                .isConfigurationSection("scoreboard");
    }
}