package it.chiarani.otlsmartcontroller.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import it.chiarani.otlsmartcontroller.db.UserDataSource;

/**
 * Factory for viewmodels
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final UserDataSource mUserDataSource;

    public  ViewModelFactory(UserDataSource userDataSource) {
        mUserDataSource = userDataSource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(mUserDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
