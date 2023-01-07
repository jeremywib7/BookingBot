package com.ww3.booking.bookingbot.services.impl;

import com.ww3.booking.bookingbot.models.Rooms;
import com.ww3.booking.bookingbot.services.StrategyService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class TextStrategy implements StrategyService {
    @Override
    public SendMessage getResponse(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());

        SendMessage response = new SendMessage();
        String defaultResponse = "I'm sorry i couldn't understand your message. Please tap /start to start over  ";
        response.setChatId(chatId);
        response.setText(defaultResponse);

        String textUpdate = update.getMessage().getText().trim();

        switch (textUpdate.toLowerCase()) {
            case "/start" -> {
                response.setText("Warmest hello from ww3 hotel. Please select from our many 5 star room");

                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
                List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

                for (String room: Rooms.ROOM_TYPE_LIST) {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(room);
                    button.setCallbackData(room);
                    buttonsRow.add(button);
                }

                keyboard.add(buttonsRow);

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(keyboard);

                response.setReplyMarkup(inlineKeyboardMarkup);
            }
            case "/end" -> {
                System.out.println("ended");
            }
            default -> {

            }
        }

        return response;
    }
}
