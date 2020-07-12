package com.charan.listeners;

import com.charan.models.BotResponse;
import com.charan.parsers.CommandParser;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceStreamEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class EventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    private final CommandParser commandParser;

    EventListener(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) {
            return;
        }

        BotResponse response = commandParser.parseCommand(message.getContentRaw());

        if (response != null) {
            event.getChannel().sendMessage(response.getMessage()).queue();
        }
    }

    @Override
    public void onGuildVoiceStream(@Nonnull GuildVoiceStreamEvent event) {
        log.info(event.isStream() + " | " + event.getMember().getEffectiveName() + " | " + event.getVoiceState().getChannel());
    }

}
