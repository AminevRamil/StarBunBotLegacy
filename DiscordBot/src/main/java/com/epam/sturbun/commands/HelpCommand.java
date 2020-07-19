package com.epam.sturbun.commands;

import com.epam.sturbun.DiscordBot;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Команда выдающая справку о командах бота
 * TODO сделать возможность получения справки по отдельным командам ("help debug", "help game" и т.п.)
 * TODO выведение справки из ресурсов проекта
 */
public class HelpCommand implements Command {

    private DiscordBot bot;
    private String answer;
    private MessageChannel targetChannel;

    public HelpCommand(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public void prepare(MessageReceivedEvent message) {
        targetChannel = message.getChannel();
        answer = "```md\n" +
                "Справочная информация по командам\nДля получения справки по отдельным командам," +
                " необходимо написать \"помощь [название_команды]\"(WIP)\n\n" +
                "**Базовые команды**\n" +
                "Помощь   - Отображение данного материала\n" +
                "Описание - Получение описания бота\n" +
                "Отладка  - Управление режимом отладки\n" +
                "```";
    }

    @Override
    public void execute() {
        targetChannel.sendMessage(answer).submit();
    }
}