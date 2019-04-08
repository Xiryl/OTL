package it.chiarani.otlsmartcontroller.db;

import android.content.Context;
import android.service.autofill.UserData;

import androidx.lifecycle.ViewModelProvider;
import it.chiarani.otlsmartcontroller.db.persistence.LocalUserDataSource;
import it.chiarani.otlsmartcontroller.db.persistence.UserDatabase;
import it.chiarani.otlsmartcontroller.viewmodels.ViewModelFactory;

/**
 * Enable the injection of data source
 */
public class Injection {

    private static final String TAG = Injection.class.getSimpleName();

    /**
     * create istance of database
     * @param context context
     * @return user data source
     */
    public static UserDataSource provideUserDataSource(Context context) {
        UserDatabase database = UserDatabase.getInstance(context);
        return new LocalUserDataSource(database.userDao());
    }

    /**
     * create viewmodelfactory
     * @param context context
     * @return new istance of viewmodelfactory
     */
    public static ViewModelFactory provideViewModelFactory(Context context) {
        UserDataSource userDataSource = provideUserDataSource(context);
        return new ViewModelFactory(userDataSource);
    }
}
