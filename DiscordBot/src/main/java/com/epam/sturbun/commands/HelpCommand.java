package com.epam.sturbun.commands;

public class HelpCommand implements Command {
    @Override
    public String execute(String command) {
        return "Справочная информация по командам\nПока есть help, about, debug [on/off]";
    }
}