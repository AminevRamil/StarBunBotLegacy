package com.epam.sturbun.exceptions;

public class CommandException extends DiscordBotException {
    public CommandException(String message) {
        super(message);
    }
}
