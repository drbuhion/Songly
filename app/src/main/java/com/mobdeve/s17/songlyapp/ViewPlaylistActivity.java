package com.mobdeve.s17.songlyapp;

import static com.mobdeve.s17.songlyapp.Playlist.playList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.squareup.okhttp.internal.DiskLruCache;
import com.google.gson.Gson;


import org.w3c.dom.Text;

public class ViewPlaylistActivity extends AppCompatActivity implements EditDialog.ExampleDialogListener {

    RecyclerView playList;
    PlaylistAdapter songAdapter;
    private TextView playlist_name;
    private TextView playlist_description;
    EditText PlaylistName;
    EditText Description;
    Button clearbtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    public static SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_playlist);

        playlist_name = findViewById(R.id.playlist_name);
        playlist_description = findViewById(R.id.description);
        PlaylistName = findViewById(R.id.edit_playlistname);
        Description = findViewById(R.id.edit_description);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        playList = findViewById(R.id.recyclerView);

        clearbtn = findViewById(R.id.clearBtn);

        songAdapter = new PlaylistAdapter(Playlist.playList);
        playList.setAdapter(songAdapter);
        playList.setLayoutManager(new LinearLayoutManager(this));

        // prefs = getSharedPreferences("MY_DATA", MODE_PRIVATE);

        // String name = prefs.getString("MY_PLAYLIST_NAME", "");
        // String desc = prefs.getString("MY_DESC", "");

        // playlist_name.setText(name);
        // playlist_description.setText(desc);

        fStore.collection("playlistinfo").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String name = documentSnapshot.getString("plist_name");
                    String desc = documentSnapshot.getString("desc");
                    playlist_name.setText(name);
                    playlist_description.setText(desc);
                }
            }
        });

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_playlist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_settings:
                openDiaglog();
                break;
            case R.id.edit_delete:
                Playlist.playList.clear();
                songAdapter.notifyDataSetChanged();
                Gson gson = new Gson();
                String json = gson.toJson(Playlist.playList);
                SharedPreferences.Editor editor = Playlist.sharedPreferences.edit();
                editor.putString("list", json);
                editor.apply();
                fStore.collection("playlistinfo").document(userId).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ViewPlaylistActivity.this, "Playlist deleted", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(ViewPlaylistActivity.this, MainActivity.class));
                        }
                    }
                });
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
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
        //prefs = getSharedPreferences("MY_DATA", MODE_PRIVATE);

       // String name = prefs.getString("MY_PLAYLIST_NAME", "");
       // String desc = prefs.getString("MY_DESC", "");

        fStore.collection("playlistinfo").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String name = documentSnapshot.getString("plist_name");
                    String desc = documentSnapshot.getString("desc");

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
        });
    }
}