package com.epam.sturbun.commands;

import com.epam.sturbun.DiscordBot;

/**
 * Команда задающая режим дебага в чате.
 */
public class DebugCommand implements Command {

    private DiscordBot bot;

    public DebugCommand(DiscordBot bot) {
        this.bot = bot;
    }

    /**
     * Оставляю место для исправления
     *
     * @param command комманда поступившая из чата
     * @return ответ для пользователя
     */
    @Override
    public String execute(String command) {
        if (command.contains("on") ^ command.contains("off")) {
            if (command.contains("on")) {
                bot.setDebugMode(true);
                return "Режим дебага активирован";
            } else if (command.contains("off")) {
                bot.setDebugMode(false);
                return "Режим дебага деактивирован";
            } else return "Команда задана неверно"; //Благодаря XOR, это условие недостижимо, но идея считает иначе.
        } else return "Команда задана неверно";
    }
}
