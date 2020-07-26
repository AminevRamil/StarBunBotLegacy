package com.epam.sturbun.handlers;

import com.epam.sturbun.DiscordBot;
import com.epam.sturbun.commands.AboutCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class AboutHandler implements CommandHandler<AboutCommand> {

    DiscordBot bot;

    public AboutHandler(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public AboutCommand prepare(MessageReceivedEvent message, DiscordBot bot) {
        AboutCommand aboutCommand = new AboutCommand();
        aboutCommand.setAnswer(makeAnswer());
        aboutCommand.setTargetChannel(message.getChannel());
        return aboutCommand;
    }

    private EmbedBuilder makeAnswer() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("О боте", "https://github.com/AminevRamil/StarBunBot");
        eb.setDescription("Бот разрабатываемый в ходе ре-тренинга в компании EPAM");
        eb.setColor(new Color(235, 192, 0));
        eb.setThumbnail("https://avatars1.githubusercontent.com/u/41346424?s=460&u=4ef760d7b73102d55baf43237e33e32c37e3b2b6&v=4");
        return eb;
    }


}
