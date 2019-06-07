package it.chiarani.otlsmartcontroller.db;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;

public interface UserDataSource {

    /**
     * Get user from database.
     * @return user from database
     */
    Flowable<User> getUser();

    /**
     * Insert user to database, or, if already exists override it.
     * @param user to be insered or update to database
     */
    Completable insertOrUpdateUser(User user);

    /**
     * Remove all users from database
     */
    void deleteAllUsers();


}
