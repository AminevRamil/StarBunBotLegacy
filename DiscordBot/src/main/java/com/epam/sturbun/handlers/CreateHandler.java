package com.epam.sturbun.handlers;

import com.epam.sturbun.DiscordBot;
import com.epam.sturbun.commands.CreateCommand;
import com.epam.sturbun.exceptions.CommandException;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

public class CreateHandler implements CommandHandler<CreateCommand> {

    DiscordBot bot;

    public CreateHandler(DiscordBot bot) {
        this.bot = bot;
    }

    private static Map<String, CreateTargetType> targetTypeMap = new HashMap<>();
    static {
        Arrays.stream(CreateTargetType.values()).forEach(createType -> {
            List<String> aliases = createType.getAliases();
            aliases.forEach(alias -> targetTypeMap.put(alias, createType));
        });
    }

    @Override
    public CreateCommand prepare(MessageReceivedEvent message, DiscordBot bot) {
        String[] args = Arrays.
                stream(message.getMessage()
                        .getContentRaw()
                        .split(" "))
                .skip(1)
                .map(String::toLowerCase)
                .toArray(String[]::new);

        CreateCommand createCommand = new CreateCommand();
        createCommand.setTargetChannel(message.getChannel());
        createCommand.setTargetGuild(message.getGuild());
        Optional<CreateTargetType> typeOptional = Optional.ofNullable(targetTypeMap.get(args[1]));
        createCommand.setTargetType(typeOptional.orElseThrow(() -> new CommandException("Несуществующий тип для создания")));
        createCommand.setTargetName(args[args.length - 1]);
        if (args.length == 4) createCommand.setTargetDist(args[2]);

        makeAnswer(createCommand);

        return createCommand;
    }

    private void makeAnswer(CreateCommand createCommand) {
        EmbedBuilder eb = new EmbedBuilder();
        switch (createCommand.getTargetType()){
            case CHANNEL:
                eb.setTitle("Создание канала");
                eb.setDescription("-----");
                break;
        }
        eb.setColor(new Color(235, 192, 0));
        eb.setThumbnail("https://avatars1.githubusercontent.com/u/41346424?s=460&u=4ef760d7b73102d55baf43237e33e32c37e3b2b6&v=4");
        createCommand.setAnswer(eb);
    }

    @Getter
    public enum CreateTargetType {
        CHANNEL(ImmutableList.<String>builder().add("канал", "channel").build()),
        CATEGORY(ImmutableList.<String>builder().add("категорию", "категория", "category").build());

        List<String> aliases;

        CreateTargetType(ImmutableList<String> aliases) {
            this.aliases = aliases;
        }
    }
}
