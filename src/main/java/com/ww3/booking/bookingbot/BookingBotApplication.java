package com.ww3.booking.bookingbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.rmi.UnknownHostException;

@SpringBootApplication
public class BookingBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingBotApplication.class, args);

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            Responder responder = new Responder();
            telegramBotsApi.registerBot(responder);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

}
