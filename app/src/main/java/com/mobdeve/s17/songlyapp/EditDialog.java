package com.mobdeve.s17.songlyapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditDialog extends AppCompatDialogFragment {

    private EditText editPlaylistName;
    private EditText editDescription;
    private ExampleDialogListener listener;
    private TextView txtplaylistname;
    private TextView txtdesx;
    SharedPreferences prefs;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        prefs = getContext().getSharedPreferences("MY_DATA", MODE_PRIVATE);

        prefs.getString("MY_PLAYLIST_NAME", "");
        prefs.getString("MY_DESC", "");


        builder.setView(view)
                .setTitle("Edit Playlist")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                    }
                })
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                       String playlistname = editPlaylistName.getText().toString();
                       String description = editDescription.getText().toString();

                       SharedPreferences.Editor editor = prefs.edit();
                       editor.putString("MY_PLAYLIST_NAME", playlistname);
                       editor.putString("MY_DESC", description);
                       editor.apply();
                       listener.applyTexts(playlistname,description);
                    }
                });
        editPlaylistName = view.findViewById(R.id.edit_playlistname);
        editDescription = view.findViewById(R.id.edit_description);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String playlistname, String description);
    }

}
