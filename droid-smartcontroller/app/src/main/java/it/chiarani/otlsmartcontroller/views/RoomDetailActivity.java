package it.chiarani.otlsmartcontroller.views;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.adapters.DeviceAdapter;
import it.chiarani.otlsmartcontroller.adapters.RoomsAdapter;
import it.chiarani.otlsmartcontroller.databinding.ActivityRoomDetailBinding;
import it.chiarani.otlsmartcontroller.db.Injection;
import it.chiarani.otlsmartcontroller.viewmodels.UserViewModel;
import it.chiarani.otlsmartcontroller.viewmodels.ViewModelFactory;

public class RoomDetailActivity extends BaseActivity {

    private ActivityRoomDetailBinding binding;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mUserViewModel;
    int roomIndex;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_room_detail;
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

        if(this.getIntent().hasExtra("ROOM_INDEX")) {
            roomIndex = this.getIntent().getIntExtra("ROOM_INDEX", -1);
        }
        else {
            // TODO: error!!
        }

        updateUI();
    }

    private void updateUI() {
        mDisposable.add(mUserViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( user -> {

                    if (user == null || user.otlRoomsList == null) {
                        return;
                    }

                    binding.activityRoomImgHeader.setImageResource(R.drawable.ic_room_kitchen);
                    binding.activityRoomDetailTxtQtaDevice.setText(String.format("%d dispositivi rilevati. Controlla la stanza: ", user.otlRoomsList.get(0).devices.size()));

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    binding.activityRoomDetailRvDevices.setLayoutManager(linearLayoutManager);

                    DeviceAdapter deviceAdapter = new DeviceAdapter(user, roomIndex);
                    binding.activityRoomDetailRvDevices.setAdapter(deviceAdapter);

                }));
    }

    @Override
    protected void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }
}
