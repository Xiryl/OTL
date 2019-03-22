package db.converters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;
import db.Entities.OTLRoomsEntity;

/**
 * convert all classes to json
 * Guide: https://android.jlelse.eu/room-persistence-library-typeconverters-and-database-migration-3a7d68837d6c
 */
public class OTLRoomsConverter {

    /**
     * Convert from json to WeatherForWeekEntity class
     */
    @TypeConverter
    public static List<OTLRoomsEntity> fromString(String value) {
        Type listType = new TypeToken<List<OTLRoomsEntity>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * Convert WeatherForWeekEntity class to json
     */
    @TypeConverter
    public static String fromOTLRoomsEntity(List<OTLRoomsEntity> weatherForWeek) {
        Gson gson = new Gson();
        return gson.toJson(weatherForWeek);
    }
}