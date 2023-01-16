package com.ww3.booking.bookingbot.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Map;

public class TelegramService {

    public static InlineKeyboardButton createInlineButton(String data) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(data);
        button.setCallbackData(data);
        return button;
    }

    public static InlineKeyboardButton createInlineButtonValue(Map.Entry<String, Double> set) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        // use string builder append to optimize performance than "+"
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(set.getKey()).append(": $").append(set.getValue());
        button.setText(stringBuilder.toString());
        button.setCallbackData(set.getKey());
        return button;
    }
}
