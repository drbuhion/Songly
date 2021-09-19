package com.mobdeve.s17.songlyapp;

import com.mobdeve.s17.songlyapp.model.addSong;

import java.util.ArrayList;


public class DataHelper {
    public static ArrayList<addSong> loadSongData() {
        ArrayList<addSong> data = new ArrayList<addSong>();

        data.add(new addSong("Sample Music 1",
                "Artist1","00:00", "link"));

        data.add(new addSong("Sample Music 2",
                "Artist2","00:00", "link"));


        return data;
    }
}
