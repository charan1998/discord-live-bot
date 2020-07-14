package com.charan.handlers.command;

import com.charan.models.BotResponse;

public interface CommandHandler {
    BotResponse handle(String[] args);
}
