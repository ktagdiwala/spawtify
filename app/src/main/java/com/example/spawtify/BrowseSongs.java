package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

public class BrowseSongs extends AppCompatActivity {

    List<SongModel> songModels = new ArrayList<>();
    SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_songs);

        RecyclerView recyclerView = findViewById(R.id.songsRecyclerView);
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());
        setUpSongModels();

        Song_RecyclerViewAdapter adapter = new Song_RecyclerViewAdapter(this, songModels);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpSongModels(){
        List<Song> songList = spawtifyRepository.getAllSongs();
        String title;
        String artist;
        String album;
        String genre;
        boolean explicit;

        for (int i = 0; i < songList.size(); i++){
            title = songList.get(i).getSongTitle();
            artist = songList.get(i).getSongArtist();
            album = songList.get(i).getSongAlbum();
            genre = songList.get(i).getSongGenre();
            explicit = songList.get(i).isExplicit();

            songModels.add(i,new SongModel
                                (title, artist, album, genre, explicit));
        }
    }

    private void toaster(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, BrowseSongs.class);
        return intent;
    }
}