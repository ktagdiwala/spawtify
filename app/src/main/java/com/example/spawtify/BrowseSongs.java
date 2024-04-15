package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Song;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrowseSongs extends AppCompatActivity implements SongRecyclerViewInterface{
    //  Used for storing int extras to place into viewValue
    //  -1 = came from MainActivity
    //  0 = edit song
    //  1 = delete song
    private static final String VIEW_VALUE = "com.example.spawtify.VIEW_VALUE";
    private final int editSong = 0;
    private final int deleteSong = 1;
    private final int mainActivity = -1;
    List<SongModel> songModels = new ArrayList<>();
    SpawtifyRepository spawtifyRepository;

    //  viewValue decides how to display browse songs
    private int viewValue = mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_songs);

        RecyclerView recyclerView = findViewById(R.id.songsRecyclerView);

        //  Create instance of SpawtifyRepository to use in setUpSongModels
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //  Set up our list of song models
        setUpSongModels();

        //  Custom method (I made it) that sets adapter based on where we came from
        //  Sets on adapter with onclick listener if we came from edit/delete song activity
        setAdapter(recyclerView);

        //  Sets up recyclerView with a linear layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setAdapter(RecyclerView recyclerView){
        //  Retrieve value that tells us which activity we came from
        viewValue = getIntent().getIntExtra(VIEW_VALUE, mainActivity);

        //  Set up default adapter (came from main activity)
        Song_RecyclerViewAdapter adapter = new Song_RecyclerViewAdapter
                (this, songModels);

        //  Set up adapter with onclick listener since we came from editSong
        if (viewValue == editSong){
            adapter = new Song_RecyclerViewAdapter
                    (this, songModels, this);
        }
        //  Set up adapter with onclick listener since we came from delete song
        if (viewValue == deleteSong){
            adapter = new Song_RecyclerViewAdapter
                    (this, songModels, this);
        }
        recyclerView.setAdapter(adapter);
    }

    private void setUpSongModels(){
        List<Song> songList = spawtifyRepository.getAllSongs();
        int songId;
        String title;
        String artist;
        String album;
        String genre;
        boolean explicit;

        for (int i = 0; i < songList.size(); i++){
            songId = songList.get(i).getSongId();
            title = songList.get(i).getSongTitle();
            artist = songList.get(i).getSongArtist();
            album = songList.get(i).getSongAlbum();
            genre = songList.get(i).getSongGenre();
            explicit = songList.get(i).isExplicit();

            songModels.add(i,new SongModel
                                (songId, title, artist, album, genre, explicit));
        }
    }

    private void toaster(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, BrowseSongs.class);
        return intent;
    }

    public static Intent intentFactory(Context context, int viewValue){
        Intent intent = new Intent(context, BrowseSongs.class);
        intent.putExtra(VIEW_VALUE, viewValue);
        return intent;
    }


    //  Method inherited from SongRecyclerViewInterface
    @Override
    public void onItemClick(int position) {
        String songTitle = songModels.get(position).getSongTitle();
        toaster("You clicked on " + songTitle + "!");

        int songId = songModels.get(position).getSongId();

        //  If view value == deleteSong value (0),
        //  then create dialog that displays when song is pressed to confirm deletion of
        //  pressed song
        if (viewValue == deleteSong){
            //  Set up dialog to confirm deletion of song
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("Delete " + songTitle + "?");
            alertBuilder.setPositiveButton("Yes", (dialog, which) -> {
                //  Removes song from database
                spawtifyRepository.removeSongById(songId);
                //  Sends user back to Admin Perks
                Intent intent = AdminPerks.intentFactory(this);
                startActivity(intent);
            });
            alertBuilder.setNegativeButton("No", (dialog, which) -> {});
            alertBuilder.create().show();
            return;
        }

        //  Sets up intent to take user to Song Edit Activity
        Intent intent = SongAddOrEdit.intentFactory(this, editSong, songId);
        startActivity(intent);
    }
}