package db.DAO;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import db.Entities.UserProfileEntity;
import it.chiarani.otlsmartcontroller.helpers.SingleLiveEvent;

@Dao
public interface UserProfileDAO {
    @Query("SELECT * FROM UserProfileEntity")
    LiveData<List<UserProfileEntity>> getAll();

    @Query("DELETE FROM UserProfileEntity")
    void deleteAll();

    @Insert
    void insert(UserProfileEntity entity);
}
