package com.mobdeve.s17.songlyapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.mobdeve.s17.songlyapp.fragments.HomeFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ArrayList<Song> songData;
    private RecyclerView rvPosts;
    private SongAdapter.RecyclerViewClickListener listener;
    SharedPreferences sharedPreferences;
    SharedPreferences pref;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    TextView playlist_name;
    TextView playlist_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(navListener);
        this.initNav();
        this.initComponents();

        playlist_name = findViewById(R.id.playlist_name);
        playlist_description = findViewById(R.id.description);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        sharedPreferences = getSharedPreferences("MY_DATA", MODE_PRIVATE);
        pref = getSharedPreferences("playList", MODE_PRIVATE);
        }

    private void initComponents(){
        DataHelper helper = new DataHelper();

        this.songData = helper.loadSongData();
        this.rvPosts = findViewById(R.id.rv);

        setOnClickListener();

        this.rvPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvPosts.setAdapter(new SongAdapter(this.songData, this.listener));
    }

    private void setOnClickListener() {
        listener = new SongAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), Player.class);
                intent.putExtra("title",songData.get(position).getTitle());
                intent.putExtra("artist",songData.get(position).getArtist());
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
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                //SharedPreferences.Editor editor1 = pref.edit();
                //editor1.clear();
                //editor.clear();
                //editor1.clear();
                //editor.apply();
                Toast.makeText(MainActivity.this,"Logged out successfully", Toast.LENGTH_SHORT).show();
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
