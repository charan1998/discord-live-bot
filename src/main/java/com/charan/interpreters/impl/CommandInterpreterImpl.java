package com.charan.interpreters.impl;

import com.charan.common.ApplicationContextProvider;
import com.charan.common.Constants;
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
        String command = event.getMessage().getContentRaw();

        if (command.equals(Constants.CHECK_COMMAND)) {
            return applicationContextProvider.getContext().getBean(CheckCommandHandler.class).handle(null);
        }
        else if (command.startsWith(Constants.NOTIFY_COMMAND)) {

            String requesterDiscordId = event.getAuthor().getId();
            String guildId = event.getGuild().getId();
            String streamerDiscordId;

            if (event.getMessage().getMentionedUsers().size() > 0) {
                List<Member> mentionedMembers = event.getMessage().getMentionedMembers();

                if (mentionedMembers.size() > 1) {
                    return new BotResponse(false, "Mention one user at a time");
                }

                streamerDiscordId = mentionedMembers.get(0).getId();
            }
            else {
                String username = command.substring(Constants.NOTIFY_COMMAND.length() + 1);
                List<Member> matchedMembers = event.getGuild().getMembersByName(username, true);
                if (matchedMembers.size() == 0) {
                    return new BotResponse(false, "Username not found");
                }
                else if (matchedMembers.size() > 1) {
                    return new BotResponse(false, "Username ambiguous. Be more specific");
                }

                streamerDiscordId = matchedMembers.get(0).getId();
                logger.info("Nickname: " + matchedMembers.get(0).getNickname());
            }

            logger.info("Requester ID: " + requesterDiscordId);
            logger.info("Streamer ID: " + streamerDiscordId);
            logger.info("Guild ID: " + guildId);

            return applicationContextProvider.getContext().getBean(NotifyCommandHandler.class)
                    .handle(new String[]{requesterDiscordId, streamerDiscordId, guildId});
        }
        return null;
    }
}
