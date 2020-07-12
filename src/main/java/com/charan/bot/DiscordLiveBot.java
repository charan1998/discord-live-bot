package com.charan.bot;

import com.charan.common.Constants;
import com.charan.listeners.EventListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DiscordLiveBot implements CommandLineRunner {

    private final EventListener eventListener;

    DiscordLiveBot(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public void run(String... args) throws Exception {
        JDABuilder builder = JDABuilder.createDefault(System.getenv(Constants.BOT_TOKEN));
        builder.setActivity(Activity.watching("TV"));
        builder.addEventListeners(eventListener);
        builder.build();
    }
}
