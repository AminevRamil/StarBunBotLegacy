package com.epam.sturbun.util;

import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class VoiceChannelCloser implements Runnable {

    VoiceChannel toClose;

    public VoiceChannelCloser(VoiceChannel toClose) {
        this.toClose = toClose;
    }

    @Override
    public void run() {
        Boolean isClosed = false;
        while (!isClosed) {
            Callable<Boolean> task = () -> {
                TimeUnit.MINUTES.sleep(1);
                if (toClose.getMembers().size() == 0) {
                    toClose.delete().submit();
                    return true;
                } else return false;
            };
            try {
                isClosed = task.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
