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
}
