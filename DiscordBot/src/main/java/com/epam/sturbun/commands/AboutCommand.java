package com.epam.sturbun.commands;

/**
 * Команда возвращающая описание бота и его возможностей.
 */
public class AboutCommand implements Command {
    @Override
    public String execute(String command) {
        return "Бот который пишется в ходе ре-треннинга";
    }
}
