package it.chiarani.otlsmartcontroller.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.security.Signature;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import it.chiarani.otlsmartcontroller.App;
import it.chiarani.otlsmartcontroller.R;
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
        CircularImageView imageView = (CircularImageView) findViewById(R.id.img);

        getViewModel().getUserData().observe(this, users -> {
            binding.activityMainWelcome.setText(users.get(0).userName);
            Glide.with(this).load(users.get(0).userPicture).into(imageView);
        });





      //  binding.img.setImageDrawable(new Dra);

       /* getViewModel().getUserData().observe(this, user -> {
            int x = 1;
        });
*/
    }


}
