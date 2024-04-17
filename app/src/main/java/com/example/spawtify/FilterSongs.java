package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spawtify.databinding.ActivityBrowseSongsBinding;
import com.example.spawtify.databinding.ActivityFilterSongsBinding;

import java.util.Objects;
import java.util.logging.Filter;

public class FilterSongs extends AppCompatActivity {

    ActivityFilterSongsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilterSongsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        wireUpDisplay();
    }

    private void wireUpDisplay(){
        Objects.requireNonNull(getSupportActionBar()).setTitle("Filter Songs");

        binding.artistFilterButton.setOnClickListener(v -> artistList());
        binding.albumFilterButton.setOnClickListener(v -> albumList());
        binding.genreFilterButton.setOnClickListener(v -> genreList());
    }

    private void genreList(){
        Intent intent = GenreList.intentFactory(this);
        startActivity(intent);
    }

    private void albumList(){
        Intent intent = AlbumList.intentFactory(this);
        startActivity(intent);
    }

    private void artistList() {
        Intent intent = ArtistList.intentFactory(this);
        startActivity(intent);
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, FilterSongs.class);
        return intent;
    }
}