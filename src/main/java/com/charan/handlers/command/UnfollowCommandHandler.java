package com.charan.handlers.command;

import com.charan.entities.Streamer;
import com.charan.entities.User;
import com.charan.services.UserService;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UnfollowCommandHandler implements CommandHandler {
    private final UserService userService;

    public UnfollowCommandHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String handle(Object[] args) {
        String requesterDiscordId = (String) args[0];
        String streamerDiscordId = (String) args[1];
        String guildId = (String) args[2];

        User requester = userService.findByDiscordIdAndGuildId(requesterDiscordId, guildId).orElse(null);
        if (requester == null) {
            return "You are not following the user";
        }

        boolean isFollowing = requester.getFollowing().stream()
                .anyMatch(streamer -> streamer.getDiscordId().equals(streamerDiscordId));

        if (!isFollowing) {
            return "You are not following the user";
        }

        Set<Streamer> following = requester.getFollowing().stream()
                .filter(streamer -> !streamer.getDiscordId().equals(requesterDiscordId)).collect(Collectors.toSet());
        requester.setFollowing(following);
        userService.update(requester);

        return "Your follow request has been removed";
    }
}
