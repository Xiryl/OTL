package db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import db.DAO.UserProfileDAO;
import db.Entities.OTLDeviceEntity;
import db.Entities.OTLRoomsEntity;
import db.Entities.UserProfileEntity;
import db.converters.OTLDeviceConverter;
import db.converters.OTLRoomsConverter;

@Database(entities = {OTLDeviceEntity.class, OTLRoomsEntity.class, UserProfileEntity.class}, version = 2)
@TypeConverters({OTLRoomsConverter.class, OTLDeviceConverter.class})

public abstract class AppDatabase extends RoomDatabase {
    public abstract UserProfileDAO userProfileDAO();
}
