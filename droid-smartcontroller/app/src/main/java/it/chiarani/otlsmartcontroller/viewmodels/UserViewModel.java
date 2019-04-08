package it.chiarani.otlsmartcontroller.viewmodels;

import androidx.lifecycle.ViewModel;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import it.chiarani.otlsmartcontroller.db.UserDataSource;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;

/**
 * View Model for the {@link it.chiarani.otlsmartcontroller.views.SignInActivity}
 */
public class UserViewModel extends ViewModel {
    private final UserDataSource mUserDataSource;

    public UserViewModel(UserDataSource userDataSource) {
        mUserDataSource = userDataSource;
    }

    public Flowable<User> getUser() {
        return mUserDataSource.getUser();
    }

    public Completable insertUser(User user) {
        return mUserDataSource.insertOrUpdateUser(user);
    }
}
