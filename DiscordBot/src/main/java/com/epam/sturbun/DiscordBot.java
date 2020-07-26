package com.epam.sturbun;

import com.epam.sturbun.exceptions.CommandException;
import com.epam.sturbun.util.WordFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.channel.voice.GenericVoiceChannelEvent;
import net.dv8tion.jda.api.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log
public class DiscordBot extends ListenerAdapter {

    @Getter
    private static final String BOT_CALLING_PREFIX = "!sbb";
    private static final long THIS_BOT_ID = 732146645961015326L;
    @Setter
    private Boolean debugMode = true;

    Filter messageFilter = new Filter(this);

    public static void main(String[] args) throws LoginException {
        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }
        DiscordBot bot = new DiscordBot();
        JDA jda = JDABuilder.createDefault(args[0], GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(bot)
                .setActivity(Activity.playing("on damn fiddle"))
                .addEventListeners()
                .build();
        jda.getEventManager().register(bot);
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        super.onReady(event);
        censoring(event);
    }

    @SubscribeEvent
    @Override
    public void onGuildVoiceLeave(@Nonnull GuildVoiceLeaveEvent event) {
        super.onGuildVoiceLeave(event);
        System.err.println("Выход");
    }

    @SubscribeEvent
    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        //super.onGuildVoiceJoin(event);
        System.err.println("Вход");
    }

    @Override
    public void onGenericVoiceChannel(@Nonnull GenericVoiceChannelEvent event) {
        super.onGenericVoiceChannel(event);
        System.err.println("Создание или удаление голосового канала");
    }

    private void censoring(@Nonnull ReadyEvent event) {
        TextChannel textChannel = event.getJDA().getTextChannelById(732200062939168840L);
        StringBuilder report = new StringBuilder();
        Map<User, List<Message>> userListMap = textChannel
                .getIterableHistory().stream()
                .takeWhile(message -> message.getAuthor().getIdLong() != THIS_BOT_ID)
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
}
