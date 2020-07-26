package com.epam.sturbun;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Launcher {
    public static void main(String[] args) throws LoginException {
        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }
        DiscordBot bot = new DiscordBot();
        JDA jda = JDABuilder.createDefault(args[0])
                .setActivity(Activity.playing("on damn fiddle"))
                .addEventListeners(bot)
                .build();
    }
}
