package com.charan.parsers.impl;

import com.charan.common.ApplicationContextProvider;
import com.charan.common.Constants;
import com.charan.handlers.CheckCommandHandler;
import com.charan.models.BotResponse;
import com.charan.parsers.CommandParser;
import org.springframework.stereotype.Component;

@Component
public class CommandParserImpl implements CommandParser {

    private final ApplicationContextProvider applicationContextProvider;

    CommandParserImpl(ApplicationContextProvider applicationContextProvider) {
        this.applicationContextProvider = applicationContextProvider;
    }

    @Override
    public BotResponse parseCommand(String command) {
        if (command.equals(Constants.CHECK_COMMAND)) {
            return applicationContextProvider.getContext().getBean(CheckCommandHandler.class).handle(null);
        }
        return null;
    }
}
