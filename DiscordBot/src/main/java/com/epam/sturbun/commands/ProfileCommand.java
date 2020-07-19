package com.epam.sturbun.commands;

import com.epam.sturbun.DiscordBot;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ProfileCommand implements Command {

    private DiscordBot bot;
    private String answer;
    private MessageChannel targetChannel;

    public ProfileCommand(DiscordBot bot){
        this.bot = bot;
    }

    @Override
    public void prepare(MessageReceivedEvent event) {
        answer = String.format("Username: %s\nId: %s\nFrom channel: %s",
                event.getAuthor().getName(),
                event.getAuthor().getId(),
                event.getChannel().getName());
        targetChannel = event.getChannel();
    }

    @Override
    public void execute() {
        targetChannel.sendMessage(answer).submit();
    }
}
