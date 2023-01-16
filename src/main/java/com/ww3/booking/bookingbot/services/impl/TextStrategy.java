package com.ww3.booking.bookingbot.services.impl;

import com.ww3.booking.bookingbot.MongoDB;
import com.ww3.booking.bookingbot.constant.BookingConstant;
import com.ww3.booking.bookingbot.enumeration.BookingStatusEnum;
import com.ww3.booking.bookingbot.models.Rooms;
import com.ww3.booking.bookingbot.services.CalculatorService;
import com.ww3.booking.bookingbot.services.StrategyService;
import com.ww3.booking.bookingbot.services.TelegramService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ww3.booking.bookingbot.constant.TelegramConstant.UNKNOWN_RESPONSE;

public class TextStrategy implements StrategyService {
    @Override
    public SendMessage getResponse(Update update) {
        String chatId = String.valueOf(update.getMessage().getChatId());

        SendMessage response = new SendMessage();
        response.setChatId(chatId);
        response.setText(UNKNOWN_RESPONSE);

        String textUpdate = update.getMessage().getText().trim();

        switch (textUpdate.toLowerCase()) {
            case "/start" -> {

                if(!MongoDB.userExists(chatId)){
                    MongoDB.insertNewUserId(chatId);
                }

                response.setText("Warmest hello from ww3 hotel. Please select from our many 5 star room");

                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
                List<InlineKeyboardButton> buttonsRow = new ArrayList<>();

                for (Map.Entry<String, Double> set : Rooms.ROOM_TYPE_LIST.entrySet()) {
                    buttonsRow.add(TelegramService.createInlineButtonValue(set));
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

        if (MongoDB.getFieldValue(BookingConstant.BOOKING_STATE, chatId).equalsIgnoreCase(String.valueOf(BookingStatusEnum.EMAIL))) {
            MongoDB.updateField(BookingConstant.EMAIL, textUpdate, chatId);

            MongoDB.updateField(BookingConstant.BOOKING_STATE, BookingStatusEnum.PAYMENT.toString(), chatId);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The final total price is going to be\n\n$")
                    .append(CalculatorService.getFinalPrice(chatId))
                    .append("\n\nTo get back to main menu tap /start");
            response.setText(stringBuilder.toString());
        }

        return response;
    }
}
