package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.viewHolders.AlbumAdapter;
import com.example.spawtify.viewHolders.AlbumRecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AlbumList extends AppCompatActivity implements AlbumRecyclerViewInterface {

    List<String> albumList = new ArrayList<>();

    SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);

        RecyclerView recyclerView = findViewById(R.id.albumRecyclerView);

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Filter by Album");

        setUpAlbumList();

        AlbumAdapter adapter = new AlbumAdapter(this, albumList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpAlbumList() {
        albumList = spawtifyRepository.getDistinctAlbums();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AlbumList.class);
        return intent;
    }

    @Override
    public void onItemClick(int position) {
        String album = albumList.get(position);
        int albumFilter = 4;
        Intent intent = BrowseSongs.intentFactory(this, albumFilter, album);
        startActivity(intent);
    }
}