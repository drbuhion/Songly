package com.mobdeve.s17.songlyapp;

import static android.content.Context.MODE_PRIVATE;
import static com.mobdeve.s17.songlyapp.Playlist.playList;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<ViewPlaylist> {
    public PlaylistAdapter(List<Song> songs) {
        this.songs = songs;
    }

    List<Song> songs;
    Context context;
    public static SharedPreferences sharedPreferences;
    @NonNull
    @Override
    public ViewPlaylist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View songView = inflater.inflate(R.layout.view_song_playlist,parent,false);
        ViewPlaylist viewHolder = new ViewPlaylist(songView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPlaylist holder, int position) {
        Song song = songs.get(position);
        TextView artist = holder.titleArtist;
        artist.setText(song.getArtist());
        TextView title = holder.titleTxt;
        title.setText(song.getTitle());
        ImageView image = holder.image;
        image.setImageResource(song.getImageId());


        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Playlist.playList.remove(position);
                Gson gson = new Gson();
                String json = gson.toJson(playList);
                SharedPreferences.Editor editor = Playlist.sharedPreferences.edit();
                editor.putString("list", json);
                editor.apply();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
}
