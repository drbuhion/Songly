package com.mobdeve.s17.songlyapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongViewHolder extends RecyclerView.ViewHolder {

    private ImageView dp;
    private TextView title, artist, year, album;

    public SongViewHolder(@NonNull View itemView) {
        super(itemView);

        this.dp = itemView.findViewById(R.id.dp);
        this.title = itemView.findViewById(R.id.title);
        this.artist = itemView.findViewById(R.id.artist);
        this.year = itemView.findViewById(R.id.date);
        this.album = itemView.findViewById(R.id.album);

    }

    public void setDp(int dp) { this.dp.setImageResource(dp); }

    public void setTitle(String title) { this.title.setText(title); }

    public void setArtist(String artist) { this.artist.setText(artist); }

    public void setYear(int year) { this.year.setText(Integer.toString(year)); }

    public void setAlbum(String album) { this.album.setText(album); }

}