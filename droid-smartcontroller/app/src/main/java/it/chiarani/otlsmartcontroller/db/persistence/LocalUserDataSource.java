package it.chiarani.otlsmartcontroller.db.persistence;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import it.chiarani.otlsmartcontroller.db.UserDataSource;
import it.chiarani.otlsmartcontroller.db.persistence.DAO.UserDao;
import it.chiarani.otlsmartcontroller.db.persistence.Entities.User;

/**
 * Use room database as data source
 */
public class LocalUserDataSource implements UserDataSource {

    private static final String TAG = LocalUserDataSource.class.getSimpleName();
    
    private final UserDao mUserDao;

    public LocalUserDataSource(UserDao userDao) {
        mUserDao = userDao;
    }

    @Override
    public Flowable<User> getUser() {
        return mUserDao.getUser();
    }

    @Override
    public Completable insertOrUpdateUser(User user) {
        return mUserDao.insert(user);
    }

    @Override
    public void deleteAllUsers() {
        mUserDao.deleteAll();
    }
}
