package com.epam.sturbun;

import com.epam.sturbun.util.WordFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.logging.Level;

@Log
public class DiscordBot extends ListenerAdapter {

    @Getter
    private static final String BOT_CALLING_PREFIX = "!sbb";
    @Setter
    private Boolean debugMode = true;

    Filter messageFilter = new Filter(this);

    public static void main(String[] args) throws LoginException {
        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }
        JDABuilder.createLight(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new DiscordBot())
                .setActivity(Activity.playing("on damn fiddle"))
                .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (messageFilter.isCommand(event.getMessage().getContentRaw())) {
            try {
                messageFilter.execute(event);
            } catch (Exception e) {
                log.log(Level.FINEST, "Exception: ", e);
                e.printStackTrace();
                if (debugMode) {
                    event.getChannel().sendMessage(String.format("```Java\n%s\n```", e.toString())).submit();
                }
            }
        } else if (WordFilter.hasBlockWords(event.getMessage().getContentRaw())) {
            try {
                event.getMessage().editMessage(WordFilter.filter(event)).submit();
            } catch (Exception e) {
                log.log(Level.FINEST, "Exception: ", e);
                e.printStackTrace();
                if (debugMode) {
                    event.getChannel().sendMessage(String.format("```Java\n%s\n```", e.toString())).submit();
                }
            }
        }
    }
}
