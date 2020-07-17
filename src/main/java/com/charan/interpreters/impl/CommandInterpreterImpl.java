package com.charan.interpreters.impl;

import com.charan.common.ApplicationContextProvider;
import com.charan.common.Constants;
import com.charan.common.Utils;
import com.charan.exceptions.MultipleMentionsException;
import com.charan.exceptions.MultipleUsersException;
import com.charan.exceptions.UsernameNotFoundException;
import com.charan.handlers.command.CheckCommandHandler;
import com.charan.handlers.command.NotifyCommandHandler;
import com.charan.models.BotResponse;
import com.charan.interpreters.CommandInterpreter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandInterpreterImpl implements CommandInterpreter {

    private static final Logger logger = LoggerFactory.getLogger(CommandInterpreterImpl.class);

    private final ApplicationContextProvider applicationContextProvider;

    CommandInterpreterImpl(ApplicationContextProvider applicationContextProvider) {
        this.applicationContextProvider = applicationContextProvider;
    }

    @Override
    public BotResponse parseCommand(MessageReceivedEvent event) {
        String command = event.getMessage().getContentRaw().toLowerCase();

        if (command.equals(Constants.CHECK_COMMAND)) {
            return applicationContextProvider.getContext().getBean(CheckCommandHandler.class).handle(null);
        }
        else if (command.startsWith(Constants.NOTIFY_COMMAND)) {

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

            return applicationContextProvider.getContext().getBean(NotifyCommandHandler.class)
                    .handle(new String[]{requesterDiscordId, streamerDiscordId, guildId});
        }
        return null;
    }

    private BotResponse handleException(Exception exception) {
        if (exception instanceof MultipleMentionsException) {
            return new BotResponse(false, "Mention one user at a time");
        }
        else if (exception instanceof UsernameNotFoundException) {
            return new BotResponse(false, "Username not found");
        }
        else if (exception instanceof MultipleUsersException) {
            return new BotResponse(false, "Username ambiguous. Be more specific");
        }
        else {
            exception.printStackTrace();
            return new BotResponse(false, "Unknown error occurred. Contact the developer");
        }
    }
}
