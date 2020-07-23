package com.epam.sturbun.handlers;

import com.epam.sturbun.DiscordBot;
import com.epam.sturbun.commands.GameCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GameHandler implements CommandHandler<GameCommand> {

    DiscordBot bot;

    public GameHandler(DiscordBot bot){
        this.bot = bot;
    }

    @Override
    public GameCommand prepare(MessageReceivedEvent message, DiscordBot bot) {
        return null;
    }
}
