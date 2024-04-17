package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivityMyPlaylistsBinding;

import java.util.ArrayList;
import java.util.List;

/** MyPlaylistActivity
 * Displays the playlists created by a user
 * Provides options to edit, create, or delete playlists
 * @author Krishna Tagdiwala
 * @since 04-15-2024
 */

public class MyPlaylistsActivity extends AppCompatActivity {

    // allows to read information from the display
    ActivityMyPlaylistsBinding binding;

    //  Used as a key to retrieve value (int: userId) from MainActivity
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";

    // Creates a user object, initialized in MainActivity
    private User user;

    // Stores current userId
    int userId = -1;

    // Creates SpawtifyRepository object initialized in OnCreate
    private SpawtifyRepository spawtifyRepository;

    // Stores the list of playlists in an array
    ArrayList<PlaylistModel> playlistModels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyPlaylistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        user.setUserId(Integer.parseInt(USER_ID_KEY));

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());
//        String setTitleString =  user.getUsername() + "'s Playlists";
//        binding.MyPlaylistsTextview.setText(setTitleString);

        setupPlaylistModels();
        Playlist_RecyclerViewAdapter adapter = new Playlist_RecyclerViewAdapter(this, spawtifyRepository.getAllUserPlaylists(userId));
        binding.MyPlaylistsRecyclerView.setAdapter(adapter);
        binding.MyPlaylistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.MyPlaylistsDescriptionTextview.setText(R.string.click_on_a_playlist_to_view_contained_songs);

        //Sets up New Playlist button
        binding.NewPlaylistButton.setOnClickListener(v -> {
            Intent intent = CreatePlaylistActivity.intentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    private void setupPlaylistModels(){
        // Retrieves a list of all the playlist titles
        // from the list of playlists associated with a user
        List<String> playlistTitles = spawtifyRepository.getAllUserPlaylistTitles(userId);

        // Retrieves a list of all the playlist descriptions
        // from the list of playlists associated with a user
        List<String> playlistDescriptions = spawtifyRepository.getAllUserPlaylistDescriptions(userId);

        // Creates a list item (PlaylistModel object) for each playlist title-description pair
        for (int i = 0; i <spawtifyRepository.getAllUserPlaylists(userId).size(); i++){
            playlistModels.add(new PlaylistModel(playlistTitles.get(i),
                    playlistDescriptions.get(i)));
        }

    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MyPlaylistsActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}