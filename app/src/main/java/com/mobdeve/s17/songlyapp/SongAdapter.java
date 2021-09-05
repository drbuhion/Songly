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

    private RecyclerViewClickListener listener;

    public SongAdapter(ArrayList<Song> SongData, RecyclerViewClickListener listener){
        this.SongData = SongData;
        this.listener = listener;
    }

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

        SongViewHolder songViewHolder = new SongViewHolder(itemView, this.listener);

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

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    @Override
    public int getItemCount() { return this.SongData.size(); }
}