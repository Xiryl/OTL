package it.chiarani.otlsmartcontroller;

import android.app.Application;

import it.chiarani.otlsmartcontroller.repositories.UserProfileRepository;

public class App extends Application {

    private UserProfileRepository repository;

    public App() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        repository = new UserProfileRepository(this);
    }

    public UserProfileRepository getRepository() {
        return repository;
    }
}
