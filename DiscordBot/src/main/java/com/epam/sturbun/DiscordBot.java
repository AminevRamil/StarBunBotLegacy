package com.epam.sturbun;

import com.epam.sturbun.exceptions.CommandException;
import com.epam.sturbun.util.Filter;
import com.epam.sturbun.util.VoiceChannelCloser;
import com.epam.sturbun.util.WordFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.logging.Level;

@Log
public class DiscordBot extends ListenerAdapter {

    @Getter
    private static final String BOT_CALLING_PREFIX = "!sbb";
    @Getter
    private static final long THIS_BOT_ID = 732146645961015326L;
    Filter messageFilter = new Filter(this);
    @Setter
    private Boolean debugMode = true;
    private Category dynamicVoiceChannels;

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);
        WordFilter.startupCensoring(event);
        Guild guild = event.getJDA().getGuildById(731154486176907289L);
        assert guild != null;
        Optional<Category> optionalCategory = guild.getCategories()
                .stream()
                .filter(isDynamicCategoryExist())
                .findAny();
        dynamicVoiceChannels = optionalCategory.orElseGet(() -> {
            // Создание категории, если нет
            try {
                return guild.createCategory("Dynamic (r)").submit().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return null; // Идея считает, что здесь без этого не обойтись
        });

        CreateVoiceChannelIfNoEmptyOne();
    }

    @NotNull
    private Predicate<Category> isDynamicCategoryExist() {
        return category -> category.getName().toLowerCase().equals("dynamic (r)");
    }

    private void CreateVoiceChannelIfNoEmptyOne() {
        assert dynamicVoiceChannels != null;
        int voiceChannelCount = dynamicVoiceChannels.getVoiceChannels().size();
        if (dynamicVoiceChannels.getVoiceChannels().stream().noneMatch(voiceChannel -> voiceChannel.getMembers().size() == 0)) {
            dynamicVoiceChannels.createVoiceChannel("DevVoiceChannel " + (voiceChannelCount + 1)).submit();
        }
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        try {
            if (messageFilter.isCommand(event.getMessage().getContentRaw())) {
                messageFilter.execute(event);
            } else if (WordFilter.hasBlockWords(event.getMessage().getContentRaw())) {
                event.getMessage().delete().submit();
                event.getChannel().sendMessage("Сообщение пользователя "
                        + event.getAuthor().getAsTag() + " было удалено в связи с цензурой").submit();
            }
        } catch (CommandException e) {
            log.log(Level.FINEST, "Exception: ", e);
            e.printStackTrace();
            if (debugMode) {
                event.getChannel().sendMessage(e.eb.build()).submit();
            }
        } catch (Exception e) {
            log.log(Level.FINEST, "Exception: ", e);
            e.printStackTrace();
            if (debugMode) {
                event.getChannel().sendMessage(String.format("```Java\n%s\n```", e.toString())).submit();
            }
        }
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        CreateVoiceChannelIfNoEmptyOne();
    }

    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        Runnable voiceChannelCloser = new VoiceChannelCloser(event.getChannelLeft());
        Thread thread = new Thread(voiceChannelCloser);
        thread.start();
    }
}
