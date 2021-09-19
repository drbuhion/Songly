package com.mobdeve.s17.songlyapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPlaylist extends RecyclerView.ViewHolder {
    public TextView titleTxt;
    public TextView titleArtist;
    public ImageView image;
    public Button removeBtn;

    public ViewPlaylist(@NonNull View itemView) {
        super(itemView);

        titleTxt = itemView.findViewById(R.id.titleTxt);
        titleArtist = itemView.findViewById(R.id.txtArtist);
        image = itemView.findViewById(R.id.image);
        removeBtn = itemView.findViewById(R.id.remove_btn);

    }
}
