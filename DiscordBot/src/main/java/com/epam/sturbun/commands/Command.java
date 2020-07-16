package com.epam.sturbun.commands;

/**
 * Интерфейс для выполнения команд приходящих боту
 */
public interface Command {
    /**
     * Метод обработки команды.
     *
     * @param command команда полученная из чата без префикса
     * @return текстовое сообщение для пользователя
     * @see com.epam.sturbun.DiscordBot для префикса
     */
    String execute(String command);
}
