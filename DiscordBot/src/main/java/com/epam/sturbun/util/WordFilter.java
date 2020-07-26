package com.epam.sturbun.util;

import com.epam.sturbun.DiscordBot;
import com.google.common.collect.ImmutableList;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WordFilter {
    final private static ImmutableList<String> blockList = ImmutableList.<String>builder()
            .add("говнокод").add("php").add("javascript").build();

    static public boolean hasBlockWords(String message) {
        message = message.toLowerCase();
        return blockList.stream().anyMatch(message::contains);
    }

    static public void startupCensoring(@Nonnull ReadyEvent event){
        TextChannel textChannel = event.getJDA().getTextChannelById(732200062939168840L);
        StringBuilder report = new StringBuilder();
        assert textChannel != null;
        Map<User, List<Message>> userListMap = textChannel
                .getIterableHistory().stream()
                .takeWhile(message -> message.getAuthor().getIdLong() != DiscordBot.getTHIS_BOT_ID())
                .filter(message -> WordFilter.hasBlockWords(message.getContentRaw()))
                .collect(Collectors.groupingBy(Message::getAuthor));
        userListMap.forEach((user, messages) -> {
            messages.forEach(message -> message.delete().submit());
            report.append("Удалено ").append(messages.size()).append(" сообщений от пользователя ").append(user.getAsTag()).append("\n");
        });
        if (report.length() != 0) {
            report.insert(0, "Доклад о цензуре канала:\n");
            textChannel.sendMessage(report).submit();
        }
    }
}
