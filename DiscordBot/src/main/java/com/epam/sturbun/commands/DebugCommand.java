package com.epam.sturbun.commands;

import com.epam.sturbun.DiscordBot;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Команда задающая режим дебага в чате.
 */
public class DebugCommand implements Command {

    private DiscordBot bot;
    private MessageChannel targetChannel;
    private String answer;
    private Boolean mode;

    public DebugCommand(DiscordBot bot) {
        this.bot = bot;
    }

    /**
     * Оставляю место для исправления
     *
     * @param message комманда поступившая из чата
     * @return ответ для пользователя
     */
    @Override
    public void prepare(MessageReceivedEvent message) {
        String command = message.getMessage().getContentRaw().substring(bot.getBOT_CALLING_PREFIX().length());
        if (command.contains("on") ^ command.contains("off")) {
            if (command.contains("on")) {
                mode = true;
                answer = "Режим дебага активирован";
            } else if (command.contains("off")) {
                mode = false;
                answer = "Режим дебага деактивирован";
            }
        } else answer = "Команда задана неверно";
        targetChannel = message.getChannel();
    }

    @Override
    public void execute() {
        if (mode != null) {
            bot.setDebugMode(mode);
        }
        targetChannel.sendMessage(answer).submit();
    }
}
