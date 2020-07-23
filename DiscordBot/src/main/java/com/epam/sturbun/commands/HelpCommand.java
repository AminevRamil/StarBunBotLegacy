package com.epam.sturbun.commands;

import net.dv8tion.jda.api.entities.MessageChannel;

/**
 * Команда выдающая справку о командах бота
 * TODO сделать возможность получения справки по отдельным командам ("help debug", "help game" и т.п.)
 * TODO выведение справки из ресурсов проекта
 */
public class HelpCommand extends Command {
    public void execute(){
        targetChannel.sendMessage(answer).submit();
    }
}