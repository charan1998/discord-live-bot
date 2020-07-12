package com.charan.parsers;

import com.charan.models.BotResponse;

public interface CommandParser {
    BotResponse parseCommand(String command);
}
