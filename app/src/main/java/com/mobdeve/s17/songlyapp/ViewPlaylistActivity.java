package com.mobdeve.s17.songlyapp;

import static com.mobdeve.s17.songlyapp.Playlist.playList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

public class ViewPlaylistActivity extends AppCompatActivity implements EditDialog.ExampleDialogListener {

    RecyclerView playList;
    PlaylistAdapter songAdapter;
    private TextView playlist_name;
    private TextView playlist_description;
    public static SharedPreferences prefs;
    EditText PlaylistName;
    EditText Description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist);
        playlist_name = (TextView) findViewById(R.id.playlist_name);
        playlist_description = (TextView) findViewById(R.id.description);
        PlaylistName = (EditText) findViewById(R.id.edit_playlistname);
        Description = (EditText) findViewById(R.id.edit_description);

        playList = findViewById(R.id.recyclerView);

        songAdapter = new PlaylistAdapter(Playlist.playList);
        playList.setAdapter(songAdapter);
        playList.setLayoutManager(new LinearLayoutManager(this));

        prefs = getSharedPreferences("MY_DATA", MODE_PRIVATE);

        String name = prefs.getString("MY_PLAYLIST_NAME", "");
        String desc = prefs.getString("MY_DESC", "");

        playlist_name.setText(name);
        playlist_description.setText(desc);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_playlist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_settings:
                _settings:
                openDiaglog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void ClearPlaylist(View view) {
        Playlist.playList.clear();
        songAdapter.notifyDataSetChanged();
        Gson gson = new Gson();
        String json = gson.toJson(Playlist.playList);
        SharedPreferences.Editor editor = Playlist.sharedPreferences.edit();
        editor.putString("list", json);
        editor.apply();
    }

    public void openDiaglog() {
        EditDialog editDialog = new EditDialog();
        editDialog.show(getSupportFragmentManager(), "edit dialog");
    }

    @Override
    public void applyTexts(String playlistname, String description) {
        prefs = getSharedPreferences("MY_DATA", MODE_PRIVATE);

        String name = prefs.getString("MY_PLAYLIST_NAME", "");
        String desc = prefs.getString("MY_DESC", "");

        if (playlistname.isEmpty()) {
            playlist_description.setText(desc);
        } else if (description.isEmpty()) {
            playlist_name.setText(name);
        } else {
            playlist_name.setText(name);
            playlist_description.setText(desc);
        }
    }
}