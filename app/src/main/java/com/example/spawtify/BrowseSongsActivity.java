package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.databinding.ActivityBrowseSongsBinding;
import com.example.spawtify.viewHolders.SongViewModel;
import com.example.spawtify.viewHolders.SonglistAdapter;

/** BrowseSongsActivity:
 * Display for user to view the current song list
 * User can also filter songs by artist/album/genre/explicit
 * @author Krishna Tagdiwala
 * @since 04-13-2024
 */
public class BrowseSongsActivity extends AppCompatActivity {

    private ActivityBrowseSongsBinding binding;
    private SpawtifyRepository repository;
    private SongViewModel songViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowseSongsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Reference to the the songViewModel
        songViewModel = new ViewModelProvider(this).get(SongViewModel.class);

        //Adds recycler view
        RecyclerView recyclerView = binding.SongDisplayRecyclerView;
        final SonglistAdapter adapter = new SonglistAdapter(new SonglistAdapter.SongDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = SpawtifyRepository.getRepository(getApplication());

        //adding an observer
        songViewModel.getAllSongs().observe(this, songs -> {
            adapter.submitList(songs);
        });
    }



    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, BrowseSongsActivity.class);
        return intent;
    }

}