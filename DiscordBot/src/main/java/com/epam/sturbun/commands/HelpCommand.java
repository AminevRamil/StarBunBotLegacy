package com.epam.sturbun.commands;

import com.beust.jcommander.Parameter;

/**
 * Команда выдающая справку о командах бота
 * TODO сделать возможность получения справки по отдельным командам ("help debug", "help game" и т.п.)
 * TODO выведение справки из ресурсов проекта
 */
public class HelpCommand extends Command {

    @Parameter(names = {"help", "помощь", "отладка"}, help = true)
    String command = "help";

    public void execute(){
        targetChannel.sendMessage(answer).submit();
    }
}