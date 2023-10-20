package me.notro.sumowarriors.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
public class Game {

    private final Player
            requester;

    private final Player
            target;
}
