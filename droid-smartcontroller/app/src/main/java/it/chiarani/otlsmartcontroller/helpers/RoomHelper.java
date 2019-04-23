package it.chiarani.otlsmartcontroller.helpers;

import it.chiarani.otlsmartcontroller.R;

public class RoomHelper {
    public RoomHelper() {

    }

    public static int getResource(RoomTypes type) {
        switch (type) {
            case KITCHEN: return R.drawable.ic_room_kitchen;
            default: return  -1;
        }
    }

    public static RoomTypes parseType(String name) {
        switch (name.toLowerCase()) {
            case "kitchen" : return RoomTypes.KITCHEN;
            default: return null;
        }
    }
}
