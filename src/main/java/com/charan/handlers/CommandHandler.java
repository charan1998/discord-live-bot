package com.charan.handlers;

import com.charan.models.BotResponse;

public interface CommandHandler {
    BotResponse handle(String[] args);
}
