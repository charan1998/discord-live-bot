package com.charan.handlers.command;

import com.charan.entities.Streamer;
import com.charan.entities.User;
import com.charan.models.BotResponse;
import com.charan.services.StreamerService;
import com.charan.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class NotifyCommandHandler implements CommandHandler {
    private final UserService userService;
    private final StreamerService streamerService;

    NotifyCommandHandler(UserService userService, StreamerService streamerService) {
        this.userService = userService;
        this.streamerService = streamerService;
    }

    @Override
    public BotResponse handle(String[] args) {
        String requesterDiscordId = args[0];
        String streamerDiscordId = args[1];
        String guildId = args[2];

        User requester = userService.findByDiscordIdAndGuildId(requesterDiscordId, guildId).orElse(null);
        if (requester == null) {
            User newUser = new User();
            newUser.setDiscordId(requesterDiscordId);
            newUser.setGuildId(guildId);
            requester = userService.save(newUser);
        }

        Streamer streamer = streamerService.findByDiscordIdAndGuildId(streamerDiscordId, guildId).orElse(null);
        if (streamer == null) {
            Streamer newStreamer = new Streamer();
            newStreamer.setDiscordId(streamerDiscordId);
            newStreamer.setGuildId(guildId);
            streamer = streamerService.save(newStreamer);
        }

        streamer.getSubscribers().add(requester);
        streamerService.update(streamer);

        requester.getFollowing().add(streamer);
        userService.update(requester);

        return new BotResponse(true, "Your notification request has been added");
    }
}