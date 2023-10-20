package me.notro.sumowarriors.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class Request {

    private final UUID
            requesterUUID;

    private final UUID
            targetUUID;
}
