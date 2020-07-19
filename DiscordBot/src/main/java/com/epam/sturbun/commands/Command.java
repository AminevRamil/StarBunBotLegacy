package com.epam.sturbun.commands;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Интерфейс для выполнения команд приходящих боту
 */
public interface Command {
    /**
     * Метод обработки команды.
     *
     * @param message команда полученная из чата без префикса
     * @return текстовое сообщение для пользователя
     * @see com.epam.sturbun.DiscordBot для префикса
     */
    void prepare(MessageReceivedEvent message);

    void execute();
}
