package me.notro.sumowarriors.managers;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.notro.sumowarriors.SumoWarriors;
import me.notro.sumowarriors.models.Request;
import me.notro.sumowarriors.utils.ChatUtils;
import me.notro.sumowarriors.utils.PlayerUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class RequestManager {

    private final SumoWarriors
            plugin;

    private final Set<Request>
            requests = new HashSet<>();

    @Getter
    private BukkitTask countdownTask;

    @Getter
    private Request request;

    public void sendRequest(@NonNull Player requester, @NonNull Player target) {
        if (PlayerUtils.samePlayer(requester, target)) return;

        Component acceptMessage = sendClickableMessage("&aAccept", "duel accept " + requester.getName());
        Component denyMessage = sendClickableMessage("&cDeny", "duel deny " + requester.getName());

        addRequest(requester, target);
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
                                                .append(Component.text(" "))
                                                .append(acceptMessage)
                                                .append(Component.text(" | ")
                                                        .color(NamedTextColor.GRAY))
                                                .append(denyMessage)))));

        ChatUtils.sendPrefixedMessage(target, "&7You have 60 seconds to accept!");
        startCountdown(requester, target);
    }

    public void startCountdown(@NonNull Player requester, @NonNull Player target) {
        Request request = plugin.getRequestManager().getRequest();
        countdownTask = new BukkitRunnable() {
            int counter = 1;

            @Override
            public void run() {
                if (counter <= 60) {
                    counter++;
                } else {
                    cancel();
                    plugin.getRequestManager().removeRequest(request);
                    ChatUtils.sendPrefixedMessage(target, "&7Duel invite from &e" + requester.getName() + " &7has been expired.");
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }


    private Component sendClickableMessage(@NonNull String message, @NonNull String command) {
        Component component = ChatUtils.fixColor(message);
        return component.clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));
    }

    public void addRequest(@NonNull Player requester, @NonNull Player target) {
        request = new Request(requester.getUniqueId(), target.getUniqueId());
        requests.add(request);
    }

    public void removeRequest(@NonNull Request request) {
        requests.remove(request);
    }

    public boolean hasRequest(@NonNull Request request) {
        return requests.contains(request);
    }
}
