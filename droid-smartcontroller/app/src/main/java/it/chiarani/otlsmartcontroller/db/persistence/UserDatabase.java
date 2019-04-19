package it.chiarani.otlsmartcontroller.db.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import it.chiarani.otlsmartcontroller.db.persistence.DAO.UserDao;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLDeviceEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.db.persistence.converters.OTLDeviceConverter;
import it.chiarani.otlsmartcontroller.db.persistence.converters.OTLRoomsConverter;

/**
 * The Room database that contains the Users table
 */
@Database(entities = {OTLDeviceEntity.class, OTLRoomsEntity.class, User.class}, version = 4, exportSchema = false)
@TypeConverters({OTLRoomsConverter.class, OTLDeviceConverter.class})
public abstract class UserDatabase extends RoomDatabase {

    private static volatile UserDatabase INSTANCE;

    public abstract UserDao userDao();

    public static UserDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UserDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "userData.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}