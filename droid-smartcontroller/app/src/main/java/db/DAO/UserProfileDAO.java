package db.DAO;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import db.Entities.UserProfileEntity;

@Dao
public interface UserProfileDAO {
    @Query("SELECT * FROM UserProfileEntity")
    LiveData<List<UserProfileEntity>> getAll();

    @Query("DELETE FROM UserProfileEntity")
    void deleteAll();

    @Insert
    void insert(UserProfileEntity entity);
}
