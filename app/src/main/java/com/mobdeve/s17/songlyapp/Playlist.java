package com.mobdeve.s17.songlyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Playlist extends AppCompatActivity {
    SongCollection songCollection = new SongCollection();
    static ArrayList<Song> playList = new ArrayList<Song>();
    public static SharedPreferences sharedPreferences;

    EditText PlaylistName;
    EditText Description;
    Button button;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        sharedPreferences = getSharedPreferences(userId, MODE_PRIVATE);
        String albums = sharedPreferences.getString("list", "");

        //sharedPreferences = getSharedPreferences("playList", MODE_PRIVATE);
        //String albums = sharedPreferences.getString("list", "");

        if(!albums.equals("")){
            TypeToken<ArrayList<Song>> token = new TypeToken<ArrayList<Song>>(){};
            Gson gson = new Gson();
            playList = gson.fromJson(albums,token.getType());
        }

        PlaylistName = (EditText) findViewById(R.id.edit_playlistname);
        Description = (EditText) findViewById(R.id.edit_description);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Playlist.this, ViewPlaylistActivity.class);
                startActivity(intent);

            }
        });

    }

    public void addToPlaylist(View view) {
        String songID = view.getContentDescription().toString();
        Song song = songCollection.searchById(songID);
        playList.add(song);
        Gson gson = new Gson();
        String json = gson.toJson(playList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("list", json);
        editor.apply();
        Toast.makeText(this, "Added to Playlist", Toast.LENGTH_SHORT).show();
    }

    public void Playlist(View view) {


    }

}