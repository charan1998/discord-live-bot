package com.charan.listeners;

import com.charan.handlers.event.StreamEventHandler;
import com.charan.models.BotResponse;
import com.charan.interpreters.CommandInterpreter;
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

    private final CommandInterpreter commandInterpreter;
    private final StreamEventHandler streamEventHandler;

    EventListener(CommandInterpreter commandInterpreter, StreamEventHandler streamEventHandler) {
        this.commandInterpreter = commandInterpreter;
        this.streamEventHandler = streamEventHandler;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) {
            return;
        }

        BotResponse response = commandInterpreter.parseCommand(event);

        if (response != null) {
            event.getChannel().sendMessage(message.getAuthor().getAsMention() + " " + response.getMessage()).queue();
        }
    }

    @Override
    public void onGuildVoiceStream(@Nonnull GuildVoiceStreamEvent event) {
        streamEventHandler.handleEvent(event);
    }

}
