package com.ww3.booking.bookingbot.services.impl;

import com.ww3.booking.bookingbot.services.StrategyService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackStrategy implements StrategyService {
    @Override
    public SendMessage getResponse(Update update) {
        return null;
    }
}
