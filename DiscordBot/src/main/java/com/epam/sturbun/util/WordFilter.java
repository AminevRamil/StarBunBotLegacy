package com.epam.sturbun.util;

import com.google.common.collect.ImmutableList;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class WordFilter {
    final private static ImmutableList<String> blockList = ImmutableList.<String>builder()
            .add("говнокод").add("php").add("javascript").build();

    static public boolean hasBlockWords(String message) {
        return blockList.stream().anyMatch(message::contains);
    }

    static public String filter(MessageReceivedEvent event) {
        String filteredMessage = event.getMessage().getContentRaw();
        for (String s : blockList) {
            filteredMessage = filteredMessage.replaceAll(s, "****");
        }
        return filteredMessage;
    }
}
