package com.mobdeve.s17.songlyapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.mobdeve.s17.songlyapp.model.addSong;

import java.io.IOException;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class Player extends AppCompatActivity {

    Button button;
    View screenView;
    int[] back_images;
    MediaPlayer mediaPlayer;

    private String audioUrl;

    public void Player(){};

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
        ImageButton btn_play = findViewById(R.id.btn_play);

        String title = "No title";
        String artist = "Unknown";
        String link = "link";

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
            link = extras.getString("songLink");
        }

        tv_song_title.setText(title);
        tv_song_artist.setText(artist);
        this.audioUrl = link;

        //addSong song = new addSong(title,artist,link,duration);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playAudio(audioUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void playAudio(String url) throws IOException {

        if (mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        /*
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);

            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            Toast.makeText(this, "ERROR: " + e, Toast.LENGTH_SHORT).show();
        } */

    }

}