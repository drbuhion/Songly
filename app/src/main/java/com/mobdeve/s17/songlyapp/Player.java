package com.mobdeve.s17.songlyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Player extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        TextView tv_song_title = findViewById(R.id.tv_song_title);
        TextView tv_song_artist = findViewById(R.id.tv_song_artist);

        String title = "No title";
        String artist = "Unknown";

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            title = extras.getString("title");
            artist = extras.getString("artist");
        }

        tv_song_title.setText(title);
        tv_song_artist.setText(artist);

    }
}