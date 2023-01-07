package com.ww3.booking.bookingbot.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StrategyService {
    SendMessage getResponse(Update update);
}
