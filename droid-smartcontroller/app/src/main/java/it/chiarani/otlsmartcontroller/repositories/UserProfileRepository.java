package it.chiarani.otlsmartcontroller.repositories;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import it.chiarani.otlsmartcontroller.db.AppDatabase;
import it.chiarani.otlsmartcontroller.db.persistence.DAO.UserDao;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;

public class UserProfileRepository {

    private UserDao userProfileDAO;

    private LiveData<List<User>> userProfileEntities;

    public UserProfileRepository(Application app){
        AppDatabase db      = Room.databaseBuilder(app.getApplicationContext(), AppDatabase.class, "appDatabase")
                .fallbackToDestructiveMigration()
                .build();
        userProfileDAO    = db.userProfileDAO();
        userProfileEntities = userProfileDAO.getAll();
    }


    public void insert(final User entity, insertResponse response) {
        /**
         * Execute insert on a new thread
         */
        Executors.newSingleThreadExecutor().execute(() -> {
            userProfileDAO.insert(entity);
            response.onResponse();
        });

    }

    public LiveData<List<User>> getAll() {
        return userProfileEntities;
    }

    public interface insertResponse {
        void onResponse();
    }
}


