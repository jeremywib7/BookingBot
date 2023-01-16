package com.ww3.booking.bookingbot;

import com.ww3.booking.bookingbot.constant.Bot;
import com.ww3.booking.bookingbot.services.TelegramService;
import com.ww3.booking.bookingbot.services.impl.CallbackStrategy;
import com.ww3.booking.bookingbot.services.impl.TextStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class Responder extends TelegramLongPollingBot {

    @Override
    public synchronized void onUpdateReceived(Update update) {

        try {
            SendMessage response = null;

            System.out.println(update);

            if (update.hasCallbackQuery()) {
                response = new CallbackStrategy().getResponse(update);
            }

            if (update.hasMessage()) {
                response = new TextStrategy().getResponse(update);
            }

            if (response == null) {
                log.error("Update type couldn't be determined.");
                return;
            }

            assert response != null;
            sendApiMethod(response);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }

//        try {
//            String response = "I'm sorry, i haven't understood the message you sent";
//            String chatId = "";
//
//            SendMessage sendMessage = new SendMessage();
//            sendMessage.setText(response);
//
//            // button clicked
//            if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null &&
//                    !update.getCallbackQuery().getData().isEmpty()) {
//                chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
//                CallbackData callBackData = CallbackData.valueOf(update.getCallbackQuery().getData().toUpperCase());
//
//                switch (callBackData) {
//                    case CD_YES -> {
//                        LocalDateTime currentTime = LocalDateTime.now();
//                        sendMessage.setText(currentTime.toLocalTime().toString());
//                    }
//                    case CD_NO -> sendMessage.setText("Alright");
//                    default -> {
//                    }
//                    // code block
//                }
//            }
//
//            if ((update.hasMessage())) {
//                chatId = String.valueOf(update.getMessage().getChatId());
//                boolean userExists = MongoDB.userExists(chatId);
//                MongoDB.insertNewUserId(chatId);
//                if (update.getMessage().hasText()) {
//                    String userMessage = update.getMessage().getText().trim();
//                    switch (userMessage) {
//                        case "/time" -> {
//                            sendMessage.setText("Would you like to know the current time? \uD83D\uDE00");
//
//                            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
//                            List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
//
//                            // create yes button
//                            InlineKeyboardButton yesButton = new InlineKeyboardButton();
//                            yesButton.setText("Yes");
//                            yesButton.setCallbackData(String.valueOf(CD_YES));
//
//                            // create no button
//                            InlineKeyboardButton noButton = new InlineKeyboardButton();
//                            noButton.setText("No thanks");
//                            noButton.setCallbackData(String.valueOf(CD_NO));
//
//                            buttonsRow.add(yesButton);
//                            buttonsRow.add(noButton);
//
//                            keyboard.add(buttonsRow);
//
//                            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//                            inlineKeyboardMarkup.setKeyboard(keyboard);
//
//                            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
//                        }
//                        case "/day" -> {
//                            DayOfWeek todayDayOfTheWeek = LocalDateTime.now().getDayOfWeek();
//                            sendMessage.setText(String.valueOf(todayDayOfTheWeek).toLowerCase());
//                        }
//                        case "Hell no" -> {
//                            sendMessage.setText("Ok then");
//                        }
//                    }
//
//                    if (userMessage.contains("contact")) {
//                        sendMessage.setText("Can you share your Telegram phone number details with us?\nSo our " +
//                                "customer service representatives can contact you");
//
//                        KeyboardButton yesButton = new KeyboardButton();
//                        yesButton.setText("Yes, share contact \uD83D\uDC4D");
//                        yesButton.setRequestContact(true);
//
//                        KeyboardButton noButton = new KeyboardButton();
//                        noButton.setText("Hell no");
//
//
//                        KeyboardRow keyboardRow = new KeyboardRow();
//                        keyboardRow.add(yesButton);
//                        keyboardRow.add(noButton);
//
//                        List<KeyboardRow> keyboardRowList = new ArrayList<>();
//                        keyboardRowList.add(keyboardRow);
//
//                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//                        replyKeyboardMarkup.setKeyboard(keyboardRowList);
//                        replyKeyboardMarkup.setOneTimeKeyboard(true);
//                        replyKeyboardMarkup.setResizeKeyboard(true);
//
//                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//
//                    }
//                }
//
//                if (update.getMessage().hasContact()) {
//                    sendMessage.setText("Thank you for sending us your phone number. We will contact you shortly!");
//
//                    String phoneNumber = update.getMessage().getContact().getPhoneNumber().trim();
//                    System.out.println(phoneNumber);
//                }
//
//            }
//
//            if (chatId.isEmpty()) {
//                throw new IllegalStateException("The chat id could not be found");
//            }
//
//            sendMessage.setChatId(chatId);
//
//            sendApiMethod(sendMessage);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public String getBotUsername() {
        return Bot.USERNAME;
    }

    @Override
    public String getBotToken() {
        return Bot.BOT_TOKEN;
    }
}
