package com.epam.sturbun.commands;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.Parameter;
import com.epam.sturbun.DiscordBot;
import com.epam.sturbun.exceptions.CommandException;
import com.google.common.collect.ImmutableList;
import lombok.Data;

/**
 * Команда задающая режим дебага в чате.
 */
@Data
public class DebugCommand extends Command {

    DiscordBot bot;
    @Parameter(names = {"debug", "отладка", "дебаг"}, arity = 1, converter = MyBooleanConverter.class)
    private Boolean mode;

    @Override
    public void execute() {
        targetChannel.sendMessage(answer).submit();
        bot.setDebugMode(mode);
    }

    static class MyBooleanConverter implements IStringConverter<Boolean> {
        ImmutableList<String> onAliases = ImmutableList.of("on", "вкл");
        ImmutableList<String> offAliases = ImmutableList.of("off", "выкл");
        @Override
        public Boolean convert(String value) {
            if (onAliases.contains(value.toLowerCase())) return true;
            else if (offAliases.contains(value.toLowerCase())) return false;
            else throw new CommandException("Неверные аргументы комманды debug");
        }
    }
}
