package com.charan.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;

@Component
public class CommandListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAuthor().isBot()) {
            return;
        }

        if (message.getContentRaw().equals("!ping")) {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!").queue(response -> {
                response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
            });
        }
    }
}
