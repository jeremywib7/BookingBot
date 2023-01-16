package com.ww3.booking.bookingbot.services;

import com.ww3.booking.bookingbot.MongoDB;
import com.ww3.booking.bookingbot.constant.BookingConstant;
import com.ww3.booking.bookingbot.models.Rooms;
import org.bson.Document;

import java.util.Objects;

public class CalculatorService {
    public static double getFinalPrice(String chatId) {
        double result = 0;

        Document customerDetails = MongoDB.getCustomerAttributes(chatId);

        String roomType = customerDetails.getString(BookingConstant.ROOM_TYPE);
        String additional = customerDetails.getString(BookingConstant.ADDITIONAL);

        result += Rooms.ROOM_TYPE_LIST.get(roomType);
        if (!Objects.equals(additional, "false")) result += Rooms.ADDITIONAL_LIST.get(additional);

        return result;
    }
}
