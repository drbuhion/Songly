package com.mobdeve.s17.songlyapp;

import java.util.ArrayList;


public class DataHelper {
    public static ArrayList<Song> loadSongData() {
        ArrayList<Song> data = new ArrayList<Song>();

        data.add(new Song(
                "S1001", "Need To Know",
                "Doja Cat",
                "Planet Her", 2021, R.drawable.planet_her));
        data.add(new Song(
                "S1002", "Butter",
                "BTS", "Butter (Hotter, Sweeter, Cooler)",
                2021, R.drawable.butter));
        data.add(new Song(
                "S1003", "Bad Habits",
                "Ed Sheeran",
                "Bad Habits", 2021, R.drawable.bad_habits));
        data.add(new Song(
                "s4", "Happier Than Ever",
                "Billie Eilish", "Happier Than Ever",
                2021, R.drawable.happier_ever));
        data.add(new Song(
                "s6", "Levitating",
                "Dua Lipa, DaBaby","Future Nostalgia",
                2020, R.drawable.future_nostalgia));
        data.add(new Song(
                "s7", "traitor",
                "Olivia Rodrigo","SOUR",
                2021, R.drawable.sour));
        data.add(new Song(
                "s8", "Woman",
                "Doja Cat", "Planet Her",
                2021, R.drawable.planet_her));
        data.add(new Song(
                "s", "Blinding Lights",
                "The Weekend","After Hours", 2020, R.drawable.after_hours));
        data.add(new Song(
                "s2", "driver license","SOUR",
                "SOUR", 2021, R.drawable.sour));
        data.add(new Song(
                "s3", "Wild Side (feat. Cardi B)",
                "Normani, Cardi B", "Wild Side",2021, R.drawable.wild_side));
        data.add(new Song(
                "s5", "Watermelon Sugar",
                "Harry Styles", "Fine Line",2019, R.drawable.watermelon_sugar));
        data.add(new Song(
                "s9", "FEVER",
                "ENHYPHEN", "BORDER:CARNIVAL",2021, R.drawable.carnival));
        data.add(new Song(
                "s10", "STAY (with Justin Bieber)",
                "The kid LAROI, Justin Bieber", "F*CK LOVE 3: OVER YOU",2021, R.drawable.over_you));

        return data;
    }
}
