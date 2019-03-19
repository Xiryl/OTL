package it.chiarani.otlsmartcontroller.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.security.Signature;

import androidx.databinding.DataBindingUtil;
import it.chiarani.otlsmartcontroller.R;
import it.chiarani.otlsmartcontroller.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {


   ActivityMainBinding binding;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutID());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String name = this.getIntent().getStringExtra("G-NAME");
        String pic = this.getIntent().getStringExtra("G-PIC");
        binding.activityMainWelcome.setText("Benvenuto "  + name.split(" ")[0]);



        CircularImageView imageView = (CircularImageView) findViewById(R.id.img);
        Glide.with(this).load(pic).into(imageView);


      //  binding.img.setImageDrawable(new Dra);
    }
}
