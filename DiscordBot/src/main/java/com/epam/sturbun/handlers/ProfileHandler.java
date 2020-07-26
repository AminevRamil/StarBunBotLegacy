package com.epam.sturbun.handlers;

import com.epam.sturbun.DiscordBot;
import com.epam.sturbun.commands.ProfileCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ProfileHandler implements CommandHandler<ProfileCommand> {

    DiscordBot bot;

    public ProfileHandler(DiscordBot bot){
        this.bot = bot;
    }

    @Override
    public ProfileCommand prepare(MessageReceivedEvent message, DiscordBot bot) {
        ProfileCommand profileCommand = new ProfileCommand();
        profileCommand.setTargetChannel(message.getChannel());

        User author = message.getAuthor();

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Данные о пользователе " + author.getName());
        eb.setThumbnail(author.getEffectiveAvatarUrl());
        eb.addField("ID", author.getId(), true);
        eb.addField("Tag", author.getAsTag(), true);
        profileCommand.setAnswer(eb);
        return profileCommand;
    }
}
