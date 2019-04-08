package it.chiarani.otlsmartcontroller.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import it.chiarani.otlsmartcontroller.db.persistence.DAO.UserDao;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLDeviceEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.db.persistence.converters.OTLDeviceConverter;
import it.chiarani.otlsmartcontroller.db.persistence.converters.OTLRoomsConverter;

@Database(entities = {OTLDeviceEntity.class, OTLRoomsEntity.class, User.class}, version = 2)
@TypeConverters({OTLRoomsConverter.class, OTLDeviceConverter.class})

public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userProfileDAO();
}
