package it.chiarani.otlsmartcontroller.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.security.Signature;

import it.chiarani.otlsmartcontroller.R;

public class MainActivity extends BaseActivity {


   // ActivityMainBinding binding;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void setActivityBinding() {
    //binding = DataBindingUtil.setContentView(this, getLayoutID());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   Intent myIntent = new Intent(MainActivity.this, SignInActivity.class);
     //  this.startActivity(myIntent);
    }
}
