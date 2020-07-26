package com.epam.sturbun.commands;

/**
 * Команда возвращающая описание бота и его возможностей.
 */
public class AboutCommand extends Command {

    @Override
    public void execute() {
        targetChannel.sendMessage(answer.build()).submit();
    }
}
