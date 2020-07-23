package com.epam.sturbun.handlers;

import com.epam.sturbun.DiscordBot;
import com.epam.sturbun.commands.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandHandler<T extends Command> {

    T prepare(MessageReceivedEvent message, DiscordBot bot);
}
