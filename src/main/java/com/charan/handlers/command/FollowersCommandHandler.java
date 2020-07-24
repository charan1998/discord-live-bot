package com.charan.handlers.command;

import com.charan.entities.Streamer;
import com.charan.services.StreamerService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FollowersCommandHandler implements CommandHandler {

    private final StreamerService streamerService;

    public FollowersCommandHandler(StreamerService streamerService) {
        this.streamerService = streamerService;
    }

    @Override
    public String handle(Object[] args) {
        String discordUserId = (String) args[0];
        Guild guild = (Guild) args[1];
        String guildId = guild.getId();

        Optional<Streamer> streamer = streamerService.findByDiscordIdAndGuildId(discordUserId, guildId);

        if (streamer.isPresent() && streamer.get().getSubscribers().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            streamer.get().getSubscribers().forEach((subscriber) -> {
                String subscriberDiscordId = subscriber.getDiscordId();

                Member member = guild.getMemberById(subscriberDiscordId);

                if (member != null) {
                    stringBuilder.append(member.getNickname() == null ? member.getUser().getName() : member.getNickname());
                    stringBuilder.append('\n');
                }
            });

            return stringBuilder.toString();
        }
        else {
            return "You have no followers";
        }
    }
}
