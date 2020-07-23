package com.epam.sturbun.commands;

import lombok.Data;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;

@Data
public abstract class Command {
    MessageEmbed answer;
    MessageChannel targetChannel;

    public abstract void execute();
}
