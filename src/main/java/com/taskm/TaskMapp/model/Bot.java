package com.taskm.TaskMapp.model;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@PropertySource("classpath:application.properties")
@Component
public class Bot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {


    @Value("${bot.token}")
    private String botToken;

    private static TelegramClient telegramClient;

    @PostConstruct
    public void init() {
        if (botToken == null) {
            throw new IllegalStateException("botToken is null");
        }
        System.out.println("Bot token: " + botToken);
        telegramClient = new OkHttpTelegramClient(botToken);
    }


    public Bot() {
    }


    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(update.getMessage().getText());
            String chatID = String.valueOf(update.getMessage().getChatId());
            System.out.println(chatID);
        }
    }

    public static void sendMessage(String message) {
        String chatID = "517963278";
        SendMessage sendMessage = new SendMessage(chatID, message);
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }
}
