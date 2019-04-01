package it.chiarani.otlsmartcontroller.helpers;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import it.chiarani.otlsmartcontroller.R;

public class RoomHelper {
    public RoomHelper() {

    }

    public static int getResource(RoomTypes type) {
        switch (type) {
            case KITCHEN: return R.drawable.ic_kitchen;
            default: return  -1;
        }
    }
}
