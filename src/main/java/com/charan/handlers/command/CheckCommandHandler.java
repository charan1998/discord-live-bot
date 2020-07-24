package com.charan.handlers.command;

import org.springframework.stereotype.Component;

@Component
public class CheckCommandHandler implements CommandHandler {

    @Override
    public String handle(Object[] args) {
        return "I'm alive!";
    }
}
