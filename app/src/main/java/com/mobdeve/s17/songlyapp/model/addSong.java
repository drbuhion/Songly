package com.mobdeve.s17.songlyapp.model;

public class addSong {
    public String songTitle, artist, album_art, songDuration, songLink, mKey;

    public addSong (String songTitle, String artist, String songDuration, String songLink) {

        if(songTitle.equals("")) {
            songTitle = "Unknown";
        }

        if(artist.equals("")) {
            artist = "Unknown";
        }

        this.songTitle = songTitle;
        this.artist = artist;
        this.songDuration = songDuration;
        this.songLink = songLink;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }
}
