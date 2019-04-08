package it.chiarani.otlsmartcontroller.viewmodels;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;
import it.chiarani.otlsmartcontroller.repositories.UserProfileRepository;

public class UserProfileViewModel extends AndroidViewModel {
    private final UserProfileRepository repository;

    private MutableLiveData<List<User>> userEntities;

    public UserProfileViewModel(@NonNull Application application, UserProfileRepository repository) {
        super(application);
        this.repository = repository;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        private final Application application;

        private final UserProfileRepository repository;

        public Factory(Application application, UserProfileRepository repository) {
            this.application = application;
            this.repository = repository;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new UserProfileViewModel(application, repository);
        }
    }


    public LiveData<List<User>> getUserData() {
        return this.repository.getAll();
    }

    // se voglio ritornare ad esempio solo le room
    public LiveData<List<OTLRoomsEntity>> update() {
        MediatorLiveData<List<OTLRoomsEntity>> stanze = new MediatorLiveData<>();

        // recupero live data origina
        LiveData<List<User>> dati = this.repository.getAll();

        stanze.addSource(dati, userEntities -> {
            // qui lo devo aggiungere
            List<OTLRoomsEntity> tmp = userEntities.get(0).otlRoomsList;
            stanze.setValue(tmp);
        });

        return stanze;
    }

    public void insertData(User en, UserProfileRepository.insertResponse response) {
        repository.insert(en, response);
    }


    // funzione public che si collega al repository e fa la getall ad esempio
    /*
    public UserProfileViewModel(@Nullable UserProfileRepository userProfileRepository) {
        if (this.repository != null) {

            // ViewModel is created per Activity, so instantiate once
            // we know the userId won't change
            return;
        }
        if (repository != null) {
            this.repository = userProfileRepository;
        }
    }*/



}
