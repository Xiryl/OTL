package it.chiarani.otlsmartcontroller.views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
import it.chiarani.otlsmartcontroller.controllers.DiscoveryInitialization;
import it.chiarani.otlsmartcontroller.viewmodels.UserViewModel;
import it.chiarani.otlsmartcontroller.viewmodels.ViewModelFactory;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mUserViewModel;

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
        mUserViewModel    = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
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

                    if (user == null) {
                        return;
                    }
                    if(user.otlRoomsList != null) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        binding.mainActivityRecyclerviewRooms.setLayoutManager(linearLayoutManager);

                        RoomsAdapter roomsAdapter = new RoomsAdapter(user);
                        binding.mainActivityRecyclerviewRooms.setAdapter(roomsAdapter);

                        GraphView graph = (GraphView) findViewById(R.id.main_activity_graph_usage);
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                                new DataPoint(0, 1),
                                new DataPoint(1, 5),
                                new DataPoint(2, 3),
                                new DataPoint(3, 2),
                                new DataPoint(4, 6),
                                new DataPoint(5, 3),
                                new DataPoint(6, 0),
                                new DataPoint(7, 6)
                        });
                        graph.addSeries(series);
                        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
                        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
                        graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
                        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#bab2ec"));
                        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#bab2ec"));
                        binding.mainActivityImgNodata.setImageDrawable(null);
                        binding.mainactivityTitleRoom.setText("Le tue stanze");
                        binding.mainActivityRlContainer.setVisibility(View.VISIBLE);
                    }
                    else{
                        binding.mainActivityImgNodata.setImageResource(R.drawable.nofound);
                        binding.mainActivityRlContainer.setVisibility(View.GONE);
                        binding.mainactivityTitleRoom.setText("Non ho trovato nessuna stanza!");
                    }

                    // update user profile
                    binding.mainActivityTxtWelcome.setText(String.format("%s %s",getResources().getString(R.string.main_welcome), user.userName.split(" ")[0]));
                    Glide.with(this).load(user.userPicture).into(binding.mainActivityImgUser);

                }));


    }

    @Override
    public void onBackPressed() {
        // nothing
    }
}
