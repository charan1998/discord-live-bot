package com.charan.interpreters;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandInterpreter {
    String parseCommand(MessageReceivedEvent event);
}
