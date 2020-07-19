package com.epam.sturbun;

import java.util.logging.Level;
import com.epam.sturbun.exceptions.DiscordBotException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;


@Log
public class DiscordBot extends ListenerAdapter {

    @Getter
    private final String BOT_CALLING_PREFIX = "!sbb";
    @Setter
    private boolean debugMode = true;

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
            MessageChannel channel = event.getChannel();
            try {
                messageFilter.execute(event);
            } catch (Exception e){
                log.log(Level.FINE, "Exception: ", e);
                e.printStackTrace();
                if (debugMode) {
                    channel.sendMessage(String.format("```Java\n%s\n```", e.toString())).submit();
                }
            }
        }
    }
}
