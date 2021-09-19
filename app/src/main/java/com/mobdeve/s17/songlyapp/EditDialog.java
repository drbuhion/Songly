package com.mobdeve.s17.songlyapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class EditDialog extends AppCompatDialogFragment {

    private EditText editPlaylistName;
    private EditText editDescription;
    private ExampleDialogListener listener;
    SharedPreferences prefs;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        prefs = getContext().getSharedPreferences("MY_DATA", MODE_PRIVATE);

        prefs.getString("MY_PLAYLIST_NAME", "");
        prefs.getString("MY_DESC", "");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


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

                        //sharedPref
                       //SharedPreferences.Editor editor = prefs.edit();
                       //editor.putString("MY_PLAYLIST_NAME", playlistname);
                       //editor.putString("MY_DESC", description);
                       //editor.apply();
                      listener.applyTexts(playlistname,description);

                        //firebase
                        userID = fAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = fStore.collection("playlistinfo").document(userID);
                        Map<String,Object> user = new HashMap<>();
                        user.put("plist_name", playlistname);
                        user.put("desc", description);
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG", "onSuccess: user Playlist is created for" + userID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("TAG", "onFailure: " + e.toString());
                            }
                        });

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
