package com.epam.sturbun.commands;


import com.google.common.collect.ImmutableList;
import lombok.Getter;

import java.util.List;

/**
 * Перечисление всех возможных типов команд от пользователя
 */
@Getter
public enum CommandType {
    HELP(ImmutableList.<String>builder().add("help", "помощь", "справка").build(), HelpCommand.class),
    ABOUT(ImmutableList.<String>builder().add("about", "описание").build(), AboutCommand.class),
    DEBUG(ImmutableList.<String>builder().add("debug", "дебаг", "отладка").build(), DebugCommand.class),
    GAME(ImmutableList.<String>builder().add("game", "игра", "играть").build(), GameCommand.class),
    PROFILE(ImmutableList.<String>builder().add("profile", "профайл", "профиль").build(), ProfileCommand.class);

    CommandType(ImmutableList<String> aliases, Class<? extends Command> correspondCommand) {
        this.aliases = aliases;
        this.correspondCommand = correspondCommand;
    }

    List<String> aliases;
    private Class<? extends Command> correspondCommand;
}
