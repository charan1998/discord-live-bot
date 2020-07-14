package com.charan.interpreters;

import com.charan.models.BotResponse;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandInterpreter {
    BotResponse parseCommand(MessageReceivedEvent event);
}
