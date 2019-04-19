package it.chiarani.otlsmartcontroller.views;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.adapters.RoomsAdapter;
import it.chiarani.otlsmartcontroller.api.AuthBodyRetrofitModel;
import it.chiarani.otlsmartcontroller.api.OTLAPI;
import it.chiarani.otlsmartcontroller.api.RetrofitAPI;
import it.chiarani.otlsmartcontroller.databinding.ActivityMainBinding;
import it.chiarani.otlsmartcontroller.db.Injection;
import it.chiarani.otlsmartcontroller.helpers.Config;
import it.chiarani.otlsmartcontroller.helpers.DiscoveryInitialization;
import it.chiarani.otlsmartcontroller.viewmodels.UserViewModel;
import it.chiarani.otlsmartcontroller.viewmodels.ViewModelFactory;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mUserViewModel;
    private RetrofitAPI mRetrofitAPI;


    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutID());
    }

    @Override
    protected void setViewModel() {
        mViewModelFactory = Injection.provideViewModelFactory(this);
        mUserViewModel =  ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RetrofitAPI mRetrofitAPI = OTLAPI.getInstance();

        AuthBodyRetrofitModel authBodyRetrofitModel = new AuthBodyRetrofitModel();
        authBodyRetrofitModel.setClientUsername(Config.CLIENT_USERNAME);

        mRetrofitAPI.auth(authBodyRetrofitModel)
                .take(1)
                .flatMap(authRetrofitModel -> {
                    String token = authRetrofitModel.getToken();
                    return mRetrofitAPI.discovery(token);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DiscoveryInitialization(mUserViewModel));

        updateUI();
    }


    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }

    private void updateUI() {
        mDisposable.add(mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( user -> {

                    if (user == null || user.otlRoomsList == null) {
                        return;
                    }

                    // update user profile
                    binding.mainActivityTxtWelcome.setText(String.format("%s %s",getResources().getString(R.string.main_welcome), user.userName.split(" ")[0]));
                    Glide.with(this).load(user.userPicture).into(binding.mainActivityImgUser);

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    binding.mainActivityRecyclerviewRooms.setLayoutManager(linearLayoutManager);

                    RoomsAdapter roomsAdapter = new RoomsAdapter(user);
                    binding.mainActivityRecyclerviewRooms.setAdapter(roomsAdapter);

                }));
    }
}
