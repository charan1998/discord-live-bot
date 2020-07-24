package com.charan.handlers.event;

import com.charan.common.Utils;
import com.charan.entities.Streamer;
import com.charan.services.StreamerService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceStreamEvent;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class StreamEventHandler {
    private final StreamerService streamerService;

    StreamEventHandler(StreamerService streamerService) {
        this.streamerService = streamerService;
    }

    public void handleEvent(GuildVoiceStreamEvent event) {
        if (event.isStream()) {
            String streamerDiscordId = event.getMember().getId();
            String streamerName = Utils.getUsername(event.getMember());
            String guildId = event.getGuild().getId();
            String guildName = event.getGuild().getName();
            String channelName = Objects.requireNonNull(event.getVoiceState().getChannel()).getName();

            Optional<Streamer> streamer = streamerService.findByDiscordIdAndGuildId(streamerDiscordId, guildId);

            Guild guild = event.getGuild();
            streamer.ifPresent(value -> value.getSubscribers().forEach((subscriber) -> {
                User user = Objects.requireNonNull(guild.getMemberById(subscriber.getDiscordId())).getUser();
                user.openPrivateChannel()
                        .queue((channel) -> channel.sendMessage("```" + streamerName + " is live on " + guildName + "(" + channelName + ")" + "```")
                        .queue());
            }));
        }
    }
}
