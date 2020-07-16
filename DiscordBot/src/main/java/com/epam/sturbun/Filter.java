package com.epam.sturbun;

import com.epam.sturbun.commands.*;
import com.epam.sturbun.exceptions.CommandExceptions;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

@Log
public class Filter {
    private Pattern commandPattern;
    private int prefixLength;
    DiscordBot bot;

    private HashMap<String, CommandType> stringCommandTypesHashMap;
    private HashMap<CommandType, Command> commandTypesCommandHashMap;

    Filter(DiscordBot discordBot) {
        this.bot = discordBot;
        prefixLength = discordBot.getBOT_CALLING_PREFIX().length() + 1;
        commandPattern = Pattern.compile("^(!sbb).*");

        stringCommandTypesHashMap = new HashMap<>();
        stringCommandTypesHashMap.put("help", CommandType.HELP);
        stringCommandTypesHashMap.put("about", CommandType.ABOUT);
        stringCommandTypesHashMap.put("debug", CommandType.DEBUG);
        stringCommandTypesHashMap.put("game", CommandType.GAME);

        commandTypesCommandHashMap = new HashMap<>();
        commandTypesCommandHashMap.put(CommandType.HELP, new HelpCommand());
        commandTypesCommandHashMap.put(CommandType.ABOUT, new AboutCommand());
        commandTypesCommandHashMap.put(CommandType.DEBUG, new DebugCommand(bot));
        commandTypesCommandHashMap.put(CommandType.GAME, new GameCommand());
    }

    public boolean isCommand(String message) {
        return commandPattern.matcher(message).matches();
    }

    public String execute(MessageReceivedEvent event) {
        String rawMessage = event.getMessage().getContentRaw().trim();

        if (rawMessage.equals("!sbb")) return "Ready for action!";
        else rawMessage = rawMessage.substring(prefixLength);

        log.info(event.getMessage().toString());

        Scanner scanner = new Scanner(rawMessage);
        String coreCommand =  scanner.next();
        CommandType type = stringCommandTypesHashMap.get(coreCommand);

        if (type == null) throw new CommandExceptions("Неверная команда");

        return commandTypesCommandHashMap.get(type).execute(rawMessage);
    }
}
