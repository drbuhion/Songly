package com.mobdeve.s17.songlyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.songlyapp.model.addSong;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {

    Context context;
    private ArrayList<addSong> SongData;

    private RecyclerViewClickListener listener;



    public SongAdapter(Context context, ArrayList<addSong> SongData, RecyclerViewClickListener listener){
        this.context = context;
        this.SongData = SongData;
        this.listener = listener;
    }

    public void setData(ArrayList<addSong> SongData){
        this.SongData.clear();
        this.SongData.addAll(SongData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View itemView = inflater.inflate(R.layout.song_view, parent, false);

        View view = LayoutInflater.from(context).inflate(R.layout.song_view, parent,false);

        SongViewHolder songViewHolder = new SongViewHolder(SongData, view, this.listener);

        return songViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        //holder.setDp(SongData.get(position).getImageId());

        addSong song = SongData.get(position);

        holder.setTitle(song.getSongTitle());
        holder.setArtist(song.getArtist());
        //holder.setYear(SongData.get(position).getYear());
        //holder.setAlbum(SongData.get(position).getAlbum());
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    @Override
    public int getItemCount() { return this.SongData.size(); }
}