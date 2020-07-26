package com.charan.interpreters.impl;

import com.charan.common.ApplicationContextProvider;
import com.charan.common.Constants;
import com.charan.common.Utils;
import com.charan.exceptions.MultipleMentionsException;
import com.charan.exceptions.MultipleUsersException;
import com.charan.exceptions.UsernameNotFoundException;
import com.charan.handlers.command.*;
import com.charan.interpreters.CommandInterpreter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommandInterpreterImpl implements CommandInterpreter {

    private static final Logger logger = LoggerFactory.getLogger(CommandInterpreterImpl.class);

    private final ApplicationContextProvider applicationContextProvider;

    public CommandInterpreterImpl(ApplicationContextProvider applicationContextProvider) {
        this.applicationContextProvider = applicationContextProvider;
    }

    @Override
    public String parseCommand(MessageReceivedEvent event) {
        String command = event.getMessage().getContentRaw().toLowerCase().split(" ")[0];

        switch (command) {
            case Constants.CHECK_COMMAND:
                return applicationContextProvider.getContext().getBean(CheckCommandHandler.class).handle(null);
            case Constants.FOLLOW_COMMAND:
            case Constants.UNFOLLOW_COMMAND:
                String requesterDiscordId = event.getAuthor().getId();
                String guildId = event.getGuild().getId();
                String streamerDiscordId;

                try {
                    streamerDiscordId = Utils.extractStreamerId(event.getGuild(), event.getMessage());
                } catch (MultipleMentionsException | UsernameNotFoundException | MultipleUsersException e) {
                    return handleException(e);
                }

                logger.info("Requester ID: " + requesterDiscordId);
                logger.info("Streamer ID: " + streamerDiscordId);
                logger.info("Guild ID: " + guildId);

                if (command.equals(Constants.FOLLOW_COMMAND)) {
                    return applicationContextProvider.getContext().getBean(FollowCommandHandler.class)
                            .handle(new String[]{requesterDiscordId, streamerDiscordId, guildId});
                } else {
                    return applicationContextProvider.getContext().getBean(UnfollowCommandHandler.class)
                            .handle(new String[]{requesterDiscordId, streamerDiscordId, guildId});
                }
            case Constants.FOLLOWERS_COMMAND:
                return applicationContextProvider.getContext().getBean(FollowersCommandHandler.class)
                        .handle(new Object[]{event.getAuthor().getId(), event.getGuild()});
            case Constants.FOLLOWING_COMMAND:
                return applicationContextProvider.getContext().getBean(FollowingCommandHandler.class)
                        .handle(new Object[]{event.getAuthor().getId(), event.getGuild()});
            case Constants.HELP_COMMAND:
                return applicationContextProvider.getContext().getBean(HelpCommandHandler.class).handle(null);
        }
        return null;
    }

    private String handleException(Exception exception) {
        if (exception instanceof MultipleMentionsException) {
            return "Error: Mention one user at a time";
        }
        else if (exception instanceof UsernameNotFoundException) {
            return "Error: Username not found";
        }
        else if (exception instanceof MultipleUsersException) {
            return "Error: Username ambiguous. Be more specific";
        }
        else {
            exception.printStackTrace();
            return "Error: Unknown error occurred. Contact the developer";
        }
    }
}
