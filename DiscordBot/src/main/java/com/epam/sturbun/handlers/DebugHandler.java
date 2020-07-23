package com.epam.sturbun.handlers;

import com.beust.jcommander.JCommander;
import com.epam.sturbun.DiscordBot;
import com.epam.sturbun.commands.DebugCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;

public class DebugHandler implements CommandHandler<DebugCommand> {

    DiscordBot bot;

    public DebugHandler(DiscordBot bot){
        this.bot = bot;
    }

    @Override
    public DebugCommand prepare(MessageReceivedEvent message, DiscordBot bot) {
        String[] args = Arrays.
                stream(message.getMessage().
                        getContentRaw().
                        split(" ")).
                skip(1).
                toArray(String[]::new);

        DebugCommand debugCommand = new DebugCommand();
        debugCommand.setTargetChannel(message.getChannel());

        JCommander.newBuilder()
                .addObject(debugCommand)
                .build()
                .parse(args);

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Режим дебага", "https://github.com/AminevRamil/StarBunBot");
        if (debugCommand.getMode()) eb.setDescription("Активирован режим отладки");
        else eb.setDescription("Режим отладки деактивирован");
        eb.setColor(new Color(235, 192, 0));
        eb.setThumbnail("https://avatars1.githubusercontent.com/u/41346424?s=460&u=4ef760d7b73102d55baf43237e33e32c37e3b2b6&v=4");
        debugCommand.setAnswer(eb.build());
        debugCommand.setBot(bot);
        return debugCommand;
    }
}
