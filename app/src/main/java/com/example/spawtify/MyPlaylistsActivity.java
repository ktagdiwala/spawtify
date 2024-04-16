package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivityMyPlaylistsBinding;

public class MyPlaylistsActivity extends AppCompatActivity {

    // allows to read information from the display
    ActivityMyPlaylistsBinding binding;

    //  Used as a key to retrieve value (int: userId) from MainActivity
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";

    // Creates a user object, initialized in MainActivity
    private User user;

    // Stores current userId
    int userId;

    //Creates SpawtifyRepository object initialized in OnCreate
    private SpawtifyRepository spawtifyRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyPlaylistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        user.setUserId(Integer.parseInt(USER_ID_KEY));

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());
//        String setTitleString =  user.getUsername() + "'s Playlists";
//        binding.MyPlaylistsTextview.setText(setTitleString);
        binding.MyPlaylistsDescriptionTextview.setText(R.string.click_on_a_playlist_to_view_contained_songs);

        //Sets up New Playlist button
        binding.NewPlaylistButton.setOnClickListener(v -> {
            Intent intent = CreatePlaylistActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MyPlaylistsActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}