package it.chiarani.otlsmartcontroller.db.persistence.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    Flowable<User> getUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insert(User entity);

    @Query("DELETE FROM User")
    void deleteAll();

}
