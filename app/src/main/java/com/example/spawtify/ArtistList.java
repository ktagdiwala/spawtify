package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.viewHolders.ArtistAdapter;
import com.example.spawtify.viewHolders.ArtistRecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtistList extends AppCompatActivity implements ArtistRecyclerViewInterface {

    //  Holds our distinct artistList retrieved from database
    List<String> artistList = new ArrayList<>();

    //  Used to interact with database
    SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_list);

        RecyclerView recyclerView = findViewById(R.id.artistRecyclerView);

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //  Sets title of activity in top left of action bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Filter by Artist");

        setUpArtistList();

        ArtistAdapter adapter = new ArtistAdapter
                (this,
                        artistList,
                        this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpArtistList() {
        artistList = spawtifyRepository.getDistinctArtists();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, ArtistList.class);
        return intent;
    }

    @Override
    public void onItemClick(int position) {
        String artist = artistList.get(position);
        int artistFilter = 3;
        Intent intent = BrowseSongs.intentFactory(this, artistFilter, artist);
        startActivity(intent);
    }
}