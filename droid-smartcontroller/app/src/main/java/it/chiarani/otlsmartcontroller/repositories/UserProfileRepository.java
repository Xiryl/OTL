package it.chiarani.otlsmartcontroller.repositories;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import db.AppDatabase;
import db.DAO.UserProfileDAO;
import db.Entities.UserProfileEntity;
import it.chiarani.otlsmartcontroller.helpers.SingleLiveEvent;

public class UserProfileRepository {

    private UserProfileDAO userProfileDAO;

    private LiveData<List<UserProfileEntity>> userProfileEntities;

    public UserProfileRepository(Application app){
        AppDatabase db      = Room.databaseBuilder(app.getApplicationContext(), AppDatabase.class, "appDatabase")
                .fallbackToDestructiveMigration()
                .build();
        userProfileDAO    = db.userProfileDAO();
        userProfileEntities = userProfileDAO.getAll();
    }


    public void insert(final UserProfileEntity entity, insertResponse response) {
        /**
         * Execute insert on a new thread
         */
        Executors.newSingleThreadExecutor().execute(() -> {
            userProfileDAO.insert(entity);
            response.onResponse();
        });

    }

    public LiveData<List<UserProfileEntity>> getAll() {
        return userProfileEntities;
    }

    public interface insertResponse {
        void onResponse();
    }
}


