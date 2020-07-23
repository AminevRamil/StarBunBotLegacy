package com.epam.sturbun.commands;

import net.dv8tion.jda.api.entities.MessageChannel;

public class ProfileCommand extends Command {
    @Override
    public void execute() {
        targetChannel.sendMessage(answer).submit();
    }
}
