package me.notro.sumowarriors.managers;

import lombok.NonNull;
import me.notro.sumowarriors.utils.ChatUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RequestManager {

    private final List<UUID> requests = new ArrayList<>();

    public void sendRequest(@NonNull Player requester, @NonNull Player target) {
        if (requester == target) {
            ChatUtils.sendPrefixedMessage(requester, "&cYou cannot send a request to yourself&7.");
            return;
        }

        Component acceptMessage = sendClickableMessage("&aAccept", "request accept " + requester.getName());
        Component denyMessage = sendClickableMessage("&cDeny", "request deny " + requester.getName());


        addRequest(target);
        ChatUtils.sendPrefixedMessage(requester, "&7You have sent a play request to &e" + target.getName() + "&7.");
        ChatUtils.sendComponentMessage(target,
                Component.text(requester.getName())
                        .color(NamedTextColor.YELLOW)
                        .append(Component.text(" sent you a request to play ")
                                .color(NamedTextColor.GRAY)
                                .append(Component.text("SumoWarriors")
                                        .color(NamedTextColor.GOLD)
                                        .append(Component.text("!")
                                                .color(NamedTextColor.GRAY)
                                                .append(acceptMessage)
                                                .append(denyMessage)))));

        ChatUtils.sendPrefixedMessage(target, "&7You have 60 seconds to accept!");
    }

    private Component sendClickableMessage(@NonNull String message, @NonNull String command) {
        Component component = ChatUtils.fixColor(message);
        component = component.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));

        return component;
    }

    public void addRequest(@NonNull Player target) {
        requests.add(target.getUniqueId());
    }

    public void removeRequest(@NonNull Player target) {
        requests.remove(target.getUniqueId());
    }

    public boolean hasRequests(@NonNull Player player) {
        return requests.contains(player.getUniqueId());
    }
}
