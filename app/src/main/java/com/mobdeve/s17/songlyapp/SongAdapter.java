package com.mobdeve.s17.songlyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    private ArrayList<Song> SongData;

    public SongAdapter(ArrayList<Song> SongData){ this.SongData = SongData; }

    public void setData(ArrayList<Song> SongData){
        this.SongData.clear();
        this.SongData.addAll(SongData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.song_view, parent, false);

        SongViewHolder songViewHolder = new SongViewHolder(itemView);

        return songViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        holder.setDp(SongData.get(position).getImageId());
        holder.setTitle(SongData.get(position).getTitle());
        holder.setArtist(SongData.get(position).getArtist());
        holder.setYear(SongData.get(position).getYear());
        holder.setAlbum(SongData.get(position).getAlbum());
    }

    @Override
    public int getItemCount() { return this.SongData.size(); }
}