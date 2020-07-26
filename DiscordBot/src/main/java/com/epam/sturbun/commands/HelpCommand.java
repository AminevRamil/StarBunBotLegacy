package com.epam.sturbun.commands;

import com.beust.jcommander.Parameter;

import java.util.List;

/**
 * Команда выдающая справку о командах бота
 * TODO сделать возможность получения справки по отдельным командам ("help debug", "help game" и т.п.)
 * TODO выведение справки из ресурсов проекта
 */
public class HelpCommand extends Command {

    public void execute(){
        targetChannel.sendMessage(answer.build()).submit();
    }
}