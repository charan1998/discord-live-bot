package com.charan.handlers;

import com.charan.models.BotResponse;
import org.springframework.stereotype.Component;

@Component
public class CheckCommandHandler implements CommandHandler {

    @Override
    public BotResponse handle(String[] args) {
        return new BotResponse(true, "I'm alive :smile:");
    }
}
