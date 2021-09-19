package com.mobdeve.s17.songlyapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.mobdeve.s17.songlyapp.model.addSong;

public class UploadSong extends AppCompatActivity {

    TextView filename;
    ProgressBar progressBar;
    Uri audioUri;
    StorageReference mStorageref;
    StorageTask mUploadTask;
    DatabaseReference referenceSongs;
    //String songsCategory;
    MediaMetadataRetriever metadataRetriever;
    byte [] art;
    String sTitle, sArtist, sAlbum_art = "", sDuration, sData, sAlbum;
    //TextView title, artist, album;
    TextView duration, dataa;
    TextInputEditText title, artist;
    //ImageView album_art;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_song);

        filename = findViewById(R.id.tv_filename);
        progressBar = findViewById(R.id.pb_UploadStatus);
        //title = findViewById(R.id.tv_new_title);
        //artist = findViewById(R.id.tv_new_artist);
        //album = findViewById(R.id.tv_new_album);
        duration = findViewById(R.id.tv_new_duration);
        dataa = findViewById(R.id.tv_new_data);
        //album_art = findViewById(R.id.iv_new_picture);
        Button back_home = findViewById(R.id.btn_us_back);

        title = findViewById(R.id.input_title);
        artist = findViewById(R.id.input_artist);

        metadataRetriever = new MediaMetadataRetriever();
        referenceSongs = FirebaseDatabase.getInstance().getReference().child("songs");
        mStorageref = FirebaseStorage.getInstance().getReference().child("songs");

        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                onDestroy();
            }
        });

    }

    public void selectAudioFile (View v) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        startActivityForResult(i,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101 && resultCode == RESULT_OK && data.getData() != null) {

            audioUri = data.getData();
            String fName = getFileName(audioUri);
            filename.setText(fName);
            metadataRetriever.setDataSource(this,audioUri);

            //art = metadataRetriever.getEmbeddedPicture();
            //Bitmap bitmap = BitmapFactory.decodeByteArray(art,0,art.length);
            //album_art.setImageBitmap(bitmap);
            //album.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            //artist.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            dataa.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
            duration.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            //title.setText(metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));

            //sTitle = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            //sArtist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            sTitle = title.getText().toString();
            sArtist = artist.getText().toString();
            sDuration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

        }
    }

    private String getFileName (Uri uri) {

        String result = null;
        if(uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null,null,null,null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut!=-1) {
                result = result.substring(cut+1);
            }
        }
        return result;
    }
    public void uploadFileToFirebase (View v) {
        if(filename.equals("No file selected")) {
            Toast.makeText(this,"Please select an audio file", Toast.LENGTH_SHORT).show();
        }
        else {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(this,"uploading in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }
        }
    }

    private void uploadFile() {
        if(audioUri != null) {
            Toast.makeText(this,"uploading, please wait", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference storageReference = mStorageref.child(System.currentTimeMillis()+"."+getFileExtenstion(audioUri));
            mUploadTask = storageReference.putFile(audioUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                addSong newSong = new addSong(sTitle, sArtist, sDuration, audioUri.toString());
                                String uploadId = referenceSongs.push().getKey();
                                referenceSongs.child(uploadId).setValue(newSong);
                        }

                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        }else {
            Toast.makeText(this,"No file selected to upload", Toast.LENGTH_SHORT).show();
        }

    }

    private String getFileExtenstion(Uri audioUri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(audioUri));
    }

}