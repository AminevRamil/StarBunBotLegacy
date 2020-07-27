package com.epam.sturbun.util;

import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.concurrent.TimeUnit;

public class VoiceChannelCloser implements Runnable {

    VoiceChannel toClose;

    public VoiceChannelCloser(VoiceChannel toClose) {
        this.toClose = toClose;
    }

    @Override
    public void run() {
        Runnable task = () -> {
            try {
                TimeUnit.MINUTES.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (toClose.getMembers().size() == 0) toClose.delete().submit();
        };
        Thread closeTask = new Thread(task);
        closeTask.start();
    }
}
