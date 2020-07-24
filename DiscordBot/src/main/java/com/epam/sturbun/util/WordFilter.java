package com.epam.sturbun.util;

import com.google.common.collect.ImmutableList;

public class WordFilter {
    final private static ImmutableList<String> blockList = ImmutableList.<String>builder()
            .add("говнокод").add("php").add("javascript").build();

    static public boolean hasBlockWords(String message) {
        message = message.toLowerCase();
        return blockList.stream().anyMatch(message::contains);
    }
}
