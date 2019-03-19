package db.DAO;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import db.Entities.OTLRoomsEntity;

@Dao
public interface OTLRoomsDAO {
    @Query("SELECT * FROM OTLRoomsEntity")
    LiveData<List<OTLRoomsEntity>> getAll();

    @Query("DELETE FROM OTLRoomsEntity")
    void deleteAll();

    @Insert
    void insert(OTLRoomsEntity entity);
}
