package com.charan.bot;

import com.charan.common.Constants;
import com.charan.listeners.CommandListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DiscordLiveBot implements CommandLineRunner {

    private final CommandListener commandListener;

    DiscordLiveBot(CommandListener commandListener) {
        this.commandListener = commandListener;
    }

    @Override
    public void run(String... args) throws Exception {
        JDABuilder builder = JDABuilder.createDefault(System.getenv(Constants.BOT_TOKEN));
        builder.setActivity(Activity.watching("Hentai"));
        builder.addEventListeners(commandListener);
        builder.build();
    }
}
