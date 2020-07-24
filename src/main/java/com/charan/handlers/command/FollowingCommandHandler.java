package com.charan.handlers.command;

import com.charan.entities.Streamer;
import com.charan.entities.User;
import com.charan.services.UserService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FollowingCommandHandler implements CommandHandler {

    private final UserService userService;

    public FollowingCommandHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String handle(Object[] args) {
        String discordUserId = (String) args[0];
        Guild guild = (Guild) args[1];
        String guildId = guild.getId();

        Optional<User> user = userService.findByDiscordIdAndGuildId(discordUserId, guildId);

        if (user.isPresent() && user.get().getFollowing().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            user.get().getFollowing().forEach((streamer) -> {
                String streamerDiscordId = streamer.getDiscordId();

                Member member = guild.getMemberById(streamerDiscordId);

                if (member != null) {
                    stringBuilder.append(member.getNickname() == null ? member.getUser().getName() : member.getNickname());
                    stringBuilder.append('\n');
                }
            });

            return stringBuilder.toString();
        }
        else {
            return "You are not following anyone";
        }
    }
}
