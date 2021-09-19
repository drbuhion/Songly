package com.mobdeve.s17.songlyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.mobdeve.s17.songlyapp.fragments.HomeFragment;
import com.mobdeve.s17.songlyapp.model.addSong;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ArrayList<addSong> songData;
    private RecyclerView rvPosts;
    private SongAdapter.RecyclerViewClickListener listener;
    private SongAdapter songAdapter;

    FirebaseStorage mStorage;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;

    SharedPreferences sharedPreferences;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(navListener);
        this.initNav();
        this.initComponents();

        sharedPreferences = getSharedPreferences("MY_DATA", MODE_PRIVATE);
        pref = getSharedPreferences("playList", MODE_PRIVATE);

        setOnClickListener();

        rvPosts = findViewById(R.id.rv);
        this.songAdapter = new SongAdapter(MainActivity.this, this.songData, this.listener);
        this.rvPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvPosts.setAdapter(songAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("songs");

        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                songData.clear();
                for (DataSnapshot dss:snapshot.getChildren()) {
                    addSong songs = dss.getValue(addSong.class);
                    songs.setmKey(dss.getKey());
                    songData.add(songs);

                    songAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),""+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();

        databaseReference.removeEventListener(valueEventListener);

    }

    private void initComponents(){
        DataHelper helper = new DataHelper();

        this.songData = helper.loadSongData();
        //this.rvPosts = findViewById(R.id.rv);
    }

    private void setOnClickListener() {
        listener = new SongAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), Player.class);
                intent.putExtra("title",songData.get(position).getSongTitle());
                intent.putExtra("artist",songData.get(position).getArtist());
                intent.putExtra("songLink",songData.get(position).getSongLink());
                startActivity(intent);
            }
        };
    }

    private void initNav(){
        final  int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
    }


        private BottomNavigationView.OnItemSelectedListener navListener = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.add:
                        //selectedFragment = new UploadFragment();
                        Intent intent = new Intent(getApplicationContext(), UploadSong.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.search:
                        Intent intent2 = new Intent(getApplicationContext(), SearchSong.class);
                        startActivity(intent2);
                        finish();
                        break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
        }
    };
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.playlist_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playlist_settings:
                Intent i = new Intent(MainActivity.this, Playlist.class);
                startActivity(i);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                SharedPreferences.Editor editor = sharedPreferences.edit();
                SharedPreferences.Editor editor1 = pref.edit();
                editor1.clear();
                editor.clear();
                editor1.clear();
                editor.apply();
                Toast.makeText(MainActivity.this,"Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
