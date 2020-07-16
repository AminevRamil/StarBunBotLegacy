package com.epam.sturbun.commands;

/**
 * Команда выдающая справку о командах бота
 * TODO сделать возможность получения справки по отдельным командам ("help debug", "help game" и т.п.)
 */
public class HelpCommand implements Command {
    @Override
    public String execute(String command) {
        return "Справочная информация по командам\nПока есть help, about, debug [on/off]";
    }
}