package com.ww3.booking.bookingbot.models;

import java.util.*;

public class Rooms {

    public final static Map<String,Double> ROOM_TYPE_LIST = new LinkedHashMap<>();

    public final static List<String> BED_PREFRENCES_LIST = new ArrayList<>(Arrays.asList(
            "1 King Bed", "1 Queen Bed", "2 Single Beds"));

    public final static Map<String,Double> ADDITIONAL_LIST = new LinkedHashMap<>();


    static {
        ROOM_TYPE_LIST.put("Standard Room", 50.0);
        ROOM_TYPE_LIST.put("Deluxe Room", 70.0);
        ROOM_TYPE_LIST.put("Premier Room", 90.0);
        ROOM_TYPE_LIST.put("Suite Room", 120.0);

        ADDITIONAL_LIST.put("Early Check-In (10AM)", 20.0);
        ADDITIONAL_LIST.put("Late Check-Out (2PM)", 10.0);
    }

}
