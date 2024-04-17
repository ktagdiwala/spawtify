package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.viewHolders.GenreAdapter;
import com.example.spawtify.viewHolders.GenreRecyclerViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenreList extends AppCompatActivity implements GenreRecyclerViewInterface {

    List<String> genreList = new ArrayList<>();
    SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);

        RecyclerView recyclerView = findViewById(R.id.genreRecyclerView);

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Filter by Genre");

        setUpGenreList();

        GenreAdapter adapter = new GenreAdapter(this, genreList, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpGenreList() {
        genreList = spawtifyRepository.getDistinctGenres();
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, GenreList.class);
        return intent;
    }

    @Override
    public void onItemClick(int position) {
        String genre = genreList.get(position);
        int genreFilter = 5;
        Intent intent = BrowseSongs.intentFactory(this, genreFilter, genre);
        startActivity(intent);
    }
}