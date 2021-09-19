package com.mobdeve.s17.songlyapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class Player extends AppCompatActivity {

    Button button;
    View screenView;
    int[] back_images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        back_images = new int[]{R.drawable.bg_beach, R.drawable.bg_brix, R.drawable.bg_door, R.drawable.bg_nostalgia,
                R.drawable.bg_sky, R.drawable.bg_sky2, R.drawable.bg_spring};

        button = findViewById(R.id.changeBgBtn);
        screenView = findViewById(R.id.linear_layout);

        TextView tv_song_title = findViewById(R.id.tv_song_title);
        TextView tv_song_artist = findViewById(R.id.tv_song_artist);

        String title = "No title";
        String artist = "Unknown";

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int array_length = back_images.length;
                Random random = new Random();
                int random_number = random.nextInt(array_length);
                screenView.setBackground(ContextCompat.getDrawable(getApplicationContext(), back_images[random_number]));
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title = extras.getString("title");
            artist = extras.getString("artist");
        }

        tv_song_title.setText(title);
        tv_song_artist.setText(artist);

    }

}