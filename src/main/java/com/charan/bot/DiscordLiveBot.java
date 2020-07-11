package com.charan.bot;

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
        JDABuilder builder = JDABuilder.createDefault("NzMxMDMwMDQyMzIyNDAzMzU5.XwgKvg.4WWZiynSXoPNB429tISfRh0w0uA");
        builder.setActivity(Activity.watching("Hentai"));
        builder.addEventListeners(commandListener);
        builder.build();
    }
}
