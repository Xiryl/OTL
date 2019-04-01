package it.chiarani.otlsmartcontroller.views;

import android.app.Application;
import android.os.Bundle;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import db.Entities.OTLRoomsEntity;
import it.chiarani.otlsmartcontroller.App;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.adapters.RoomsAdapter;
import it.chiarani.otlsmartcontroller.databinding.ActivityMainBinding;
import it.chiarani.otlsmartcontroller.viewmodels.UserProfileViewModel;

public class MainActivity extends BaseActivity {


   ActivityMainBinding binding;
   UserProfileViewModel viewModel;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutID());
    }

    protected UserProfileViewModel getViewModel() {
        if(viewModel == null) {
            Application application = getApplication();
            UserProfileViewModel.Factory factory = new UserProfileViewModel.Factory(application, ((App)getApplication()).getRepository());
            viewModel = ViewModelProviders.of(this, factory).get(UserProfileViewModel.class);
        }
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateUI();
    }

    private void updateUI() {
        getViewModel().getUserData().observe(this, users -> {

            List<OTLRoomsEntity> roomsEntities = new ArrayList<>();
            OTLRoomsEntity room = new OTLRoomsEntity();
            room.roomName = "cucina";
            room.roomLocation = "kitchen";
            roomsEntities.add(room) ;

            users.get(0).otlRoomsList = roomsEntities;


            binding.mainActivityTxtWelcome.setText(String.format("Benvenuto %s", users.get(0).userName.split(" ")[0]));
            Glide.with(this).load(users.get(0).userPicture).into(binding.mainActivityImgUser);

            LinearLayoutManager linearLayoutManagerslot = new LinearLayoutManager(this);
            linearLayoutManagerslot.setOrientation(LinearLayoutManager.HORIZONTAL);
            binding.mainActivityRecyclerviewRooms.setLayoutManager(linearLayoutManagerslot);

            RoomsAdapter roomsAdapter = new RoomsAdapter(users.get(0));
            binding.mainActivityRecyclerviewRooms.setAdapter(roomsAdapter);

        });
    }


}
