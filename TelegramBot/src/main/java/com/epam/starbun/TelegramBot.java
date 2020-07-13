package com.epam.starbun;


import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
    }

    public void onUpdateReceived(Update update) {

    }

    public void onUpdatesReceived(List<Update> updates) {

    }

    public String getBotUsername() {
        return "@StarBunBot";
    }

    public String getBotToken() {
        return "1104610248:AAGjHKyTkKkI9wx8LJ8G4CbwGNRFvs4LY7Y";
    }
}
