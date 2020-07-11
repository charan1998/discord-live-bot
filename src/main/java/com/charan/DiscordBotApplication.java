package com.charan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class DiscordBotApplication {
    public static void main(String[] args) throws LoginException {
        SpringApplication.run(DiscordBotApplication.class, args);
    }
}