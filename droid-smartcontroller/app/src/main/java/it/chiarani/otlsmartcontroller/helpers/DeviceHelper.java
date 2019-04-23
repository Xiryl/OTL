package it.chiarani.otlsmartcontroller.helpers;

import it.chiarani.otlsmartcontroller.R;

public class DeviceHelper {
    public DeviceHelper() {

    }

    public static int getResource(DeviceTypes type) {
        switch (type) {
            case LIGHT: return R.drawable.ic_lamp;
            default: return  -1;
        }
    }

    public static DeviceTypes parseType(String name) {
        switch (name.toLowerCase()) {
            case "light" : return DeviceTypes.LIGHT;
            default: return null;
        }
    }
}
