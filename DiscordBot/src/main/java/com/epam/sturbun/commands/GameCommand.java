package com.epam.sturbun.commands;

import com.epam.sturbun.DiscordBot;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Команда для начала текстовой игры и настройки параметров игровой сессии.
 */
public class GameCommand implements Command {

    private DiscordBot bot;
    private String answer;
    private MessageChannel targetChannel;

    public GameCommand(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public void prepare(MessageReceivedEvent message) {
        answer = "UNDER CONSTRUCTION";
        targetChannel = message.getChannel();
    }

    @Override
    public void execute() {
        targetChannel.sendMessage(answer).submit();
    }
}