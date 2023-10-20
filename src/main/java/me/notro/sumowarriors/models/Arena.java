package me.notro.sumowarriors.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

import me.notro.sumowarriors.SumoWarriors;

@Getter
@RequiredArgsConstructor
public class Arena {

    private final SumoWarriors
            plugin;

    private final String
            name;

    private final UUID
            uuid = UUID.randomUUID();
}