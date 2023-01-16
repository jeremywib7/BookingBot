package com.ww3.booking.bookingbot.services.impl;

import com.ww3.booking.bookingbot.MongoDB;
import com.ww3.booking.bookingbot.constant.BookingConstant;
import com.ww3.booking.bookingbot.enumeration.BookingStatusEnum;
import com.ww3.booking.bookingbot.models.Rooms;
import com.ww3.booking.bookingbot.services.StrategyService;
import com.ww3.booking.bookingbot.services.TelegramService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ww3.booking.bookingbot.constant.BookingConstant.ROOM_TYPE;
import static com.ww3.booking.bookingbot.constant.TelegramConstant.UNKNOWN_RESPONSE;

public class CallbackStrategy implements StrategyService {
    @Override
    public SendMessage getResponse(Update update) {
        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(UNKNOWN_RESPONSE);

        String callbackResponse = update.getCallbackQuery().getData();

        if (Rooms.ROOM_TYPE_LIST.containsKey(callbackResponse)) {
            MongoDB.updateField(ROOM_TYPE, callbackResponse, chatId);
            response.setText("Which bed do you prefer ?");

            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            // button row
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (String bed : Rooms.BED_PREFRENCES_LIST) {
                buttonsRow.add(TelegramService.createInlineButton(bed));
            }

            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);
        }

        if (Rooms.BED_PREFRENCES_LIST.contains(callbackResponse)) {
            MongoDB.updateField(BookingConstant.BED_PREFERENCES, callbackResponse, chatId);

            response.setText("Do you have additional requests?");

            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

            for (Map.Entry<String, Double> set : Rooms.ADDITIONAL_LIST.entrySet()) {
                buttonsRow.add(TelegramService.createInlineButtonValue(set));
            }
            InlineKeyboardButton noButton = new InlineKeyboardButton();
            noButton.setText("No");
            noButton.setCallbackData("false");
            buttonsRow.add(noButton);

            keyboard.add(buttonsRow);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(keyboard);

            response.setReplyMarkup(inlineKeyboardMarkup);
        }

        if (Rooms.ADDITIONAL_LIST.containsKey(callbackResponse) || Objects.equals(callbackResponse, "false")) {
            MongoDB.updateField(BookingConstant.ADDITIONAL, callbackResponse, chatId);
            MongoDB.updateField(BookingConstant.BOOKING_STATE, String.valueOf(BookingStatusEnum.EMAIL), chatId);
            response.setText("Please write your email address");
        }

        return response;
    }
}
