package com.epam.sturbun;

import com.epam.sturbun.commands.Command;
import com.epam.sturbun.commands.CommandType;
import com.epam.sturbun.exceptions.CommandExceptions;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Log
public class Filter {
    private Pattern commandPattern;
    private int prefixLength;
    DiscordBot bot;

    private static final Map<String, Class<? extends Command>> stringToCommand = new HashMap<>();

    static {
        // Пока не осилил как в Guava создать карту с множественным ключом для одного значения
        Arrays.stream(CommandType.values()).forEach(commandType -> {
            List<String> aliases = commandType.getAliases();
            aliases.forEach(alias -> stringToCommand.put(alias, commandType.getCorrespondCommand()));
        });
    }

    Filter(DiscordBot discordBot) {
        this.bot = discordBot;
        prefixLength = discordBot.getBOT_CALLING_PREFIX().length() + 1;
        commandPattern = Pattern.compile("^(!sbb).*");
    }

    public boolean isCommand(String message) {
        return commandPattern.matcher(message).matches();
    }

    public void execute(MessageReceivedEvent event) throws Exception {
        String rawMessage = event.getMessage().getContentRaw().trim();

        if (rawMessage.equals("!sbb")) {
            event.getChannel().sendMessage("Онлайн! Напишите помощь/справка/help для получения справки").submit();
            return;
        } else rawMessage = rawMessage.substring(prefixLength);

        log.info("Получено сообщение: " + event.getMessage().toString());

        String coreCommand = rawMessage.split("\\s")[0].toLowerCase();
        Class<? extends Command> commandClass = stringToCommand.get(coreCommand);
        if (commandClass == null) throw new CommandExceptions("Несуществующая команда");

        Command command = commandClass.getDeclaredConstructor(DiscordBot.class).newInstance(bot);
        command.prepare(event);
        command.execute();
    }
}
