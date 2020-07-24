package com.epam.sturbun.commands;

public class ProfileCommand extends Command {
    @Override
    public void execute() {
        targetChannel.sendMessage(answer).submit();
    }
}
