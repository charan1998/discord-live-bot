package com.charan.handlers.command;

import com.charan.common.Constants;
import org.springframework.stereotype.Component;

@Component
public class HelpCommandHandler implements CommandHandler {

    @Override
    public String handle(Object[] args) {

        return Constants.CHECK_COMMAND + " : Check if the bot is online\n" +
                Constants.FOLLOW_COMMAND + " <username/mention> : Follow a user\n" +
                Constants.UNFOLLOW_COMMAND + " <username/mention> : Unfollow a user\n" +
                Constants.FOLLOWERS_COMMAND + " : List all followers\n" +
                Constants.FOLLOWING_COMMAND + " : List all users you are following";
    }
}
