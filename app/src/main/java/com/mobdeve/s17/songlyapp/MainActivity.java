package com.mobdeve.s17.songlyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mobdeve.s17.songlyapp.fragments.HomeFragment;
import com.mobdeve.s17.songlyapp.fragments.SearchFragment;
import com.mobdeve.s17.songlyapp.fragments.UploadFragment;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ArrayList<Song> songData;
    private RecyclerView rvPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(navListener);
        this.initNav();
        this.initComponents();
        }

    private void initComponents(){
        DataHelper helper = new DataHelper();

        this.songData = helper.loadSongData();
        this.rvPosts = findViewById(R.id.rv);

        this.rvPosts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.rvPosts.setAdapter(new SongAdapter(this.songData));
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
}
