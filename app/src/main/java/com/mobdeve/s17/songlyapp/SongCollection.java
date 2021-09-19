package com.mobdeve.s17.songlyapp;

public class SongCollection {
    public Song[] songs = new Song[13];

    public SongCollection() {

        prepareSongs();
    }

    private void prepareSongs() {

        Song NeedToKnow = new Song(
                "S1001", "Need To Know",
                "Doja Cat",
                "Planet Her", 2021, R.drawable.planet_her);
        Song Butter =  new Song("S1002", "Butter",
                "BTS", "Butter (Hotter, Sweeter, Cooler)",
                2021, R.drawable.butter);
        Song BadHabits = new Song(
                "S1003", "Bad Habits",
                "Ed Sheeran",
                "Bad Habits", 2021, R.drawable.bad_habits);
        Song HappierThanEver = new Song(
                "s4", "Happier Than Ever",
                "Billie Eilish", "Happier Than Ever",
                2021, R.drawable.happier_ever);
        Song Levitating = new Song(
                "s6", "Levitating",
                "Dua Lipa, DaBaby","Future Nostalgia",
                2020, R.drawable.future_nostalgia);
        Song Traitor = new Song(
                "s7", "traitor",
                "Olivia Rodrigo","SOUR",
                2021, R.drawable.sour);
        Song Woman = new Song(
                "s8", "Woman",
                "Doja Cat", "Planet Her",
                2021, R.drawable.planet_her);
        Song BlindingLights = new Song(
                "s", "Blinding Lights",
                "The Weekend","After Hours", 2020, R.drawable.after_hours);
        Song DriverLicense = new Song(
                "s2", "driver license","SOUR",
                "SOUR", 2021, R.drawable.sour);
        Song WildSide = new Song(
                "s3", "Wild Side (feat. Cardi B)",
                "Normani, Cardi B", "Wild Side",2021, R.drawable.wild_side);
        Song WatermelonSugar = new Song(
                "s5", "Watermelon Sugar",
                "Harry Styles", "Fine Line",2019, R.drawable.watermelon_sugar);
        Song Fever = new Song(
                "s9", "FEVER",
                "ENHYPHEN", "BORDER:CARNIVAL",2021, R.drawable.carnival);
        Song Stay = new Song(
                "s10", "STAY (with Justin Bieber)",
                "The kid LAROI, Justin Bieber", "F*CK LOVE 3: OVER YOU",2021, R.drawable.over_you);

        songs[0] = NeedToKnow;
        songs[1] = Butter;
        songs[2] = BadHabits;
        songs[3] = HappierThanEver;
        songs[4] = Levitating;
        songs[5] = Traitor;
        songs[6] = Woman;
        songs[7] = BlindingLights;
        songs[8] = DriverLicense;
        songs[9] = WildSide;
        songs[10] = WatermelonSugar;
        songs[11] = Fever;
        songs[12] = Stay;

    }

    public Song searchById(String id){
        Song tempSong = null;
        for (int i = 0; i < songs.length; i++) {
            tempSong = songs[i];
            String temptitle = tempSong.getId();
            if (temptitle.equals(id)){
                return tempSong;
            }
        }
        return tempSong;
    }

}
