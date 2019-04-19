package it.chiarani.otlsmartcontroller.views;

import androidx.appcompat.app.AppCompatActivity;
import it.chiarani.otlsmartcontroller.R;

import android.os.Bundle;
import android.widget.ImageView;

public class RoomDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        ImageView img = findViewById(R.id.activity_room_img_header);
        img.setImageResource(R.drawable.ic_room_kitchen);
    }
}
