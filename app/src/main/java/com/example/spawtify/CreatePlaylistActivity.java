package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Playlist;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivityCreatePlaylistBinding;

import java.util.Objects;


public class CreatePlaylistActivity extends AppCompatActivity {

    // Used as a key to retrieve value (int: userId) from MainActivity
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";

    // Stores current userId
    int userId = -1;

    User user;

    ActivityCreatePlaylistBinding binding;

    private String playlistTitle;
    private String playlistDescription;

    private SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePlaylistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        // Converts extra (String) to int and assigns it to userId
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Create a Playlist");

        // Checks to ensure that a valid user exists for the given userId
        if(spawtifyRepository.getUserById(userId) == null){
            toaster("Error passing userId, we have a problem.");
            Intent intent = MainActivity.intentFactory(getApplicationContext(), userId);
            startActivity(intent);
        }

        // Sets the current user object based on userId
        user = spawtifyRepository.getUserById(userId);
        // sets up Create button
        binding.FinishCreatePlaylistButton.setOnClickListener(v -> {
            // sets the playlist title to the text input
            playlistTitle = binding.PlaylistTitleEditText.getText().toString();
            // sets the playlist description to the text input
            playlistDescription = binding.PlaylistDescriptionEditText.getText().toString();

//            // creates new playlist if requirements are met and returns to the MyPlaylistsActivity view
            if(checkRequirements(playlistTitle, playlistDescription)){
                Playlist newPlaylist = new Playlist(playlistTitle, playlistDescription, userId);
                spawtifyRepository.insertPlaylist(newPlaylist);
//
                Intent intent = MyPlaylistsActivity.intentFactory(getApplicationContext(), userId);
                startActivity(intent);
            }
//
        });
    }

    /** checkRequirements:
     * Checks if title field is empty
     * Checks if description field is empty
     * Checks if playlist title is already in use
     * @return True if title and description meet requirements
     * and false if either fails to meet the requirements
     */
    private boolean checkRequirements(String title, String description){
        if (title.isEmpty()){
            toaster("Title field cannot be empty");
            return false;
        }
        if (description.isEmpty()){
            toaster("Description field cannot be empty");
            return false;
        }
        if(spawtifyRepository.getPlaylistByTitle(title, userId) != null){
            toaster("Playlist name already in use");
            return false;
        }
        toaster("\"" + title + "\" playlist has been created.");
        return true;
    }

    public static Intent intentFactory(Context context, int userId) {
        Intent intent = new Intent(context, CreatePlaylistActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }

    /** toaster
     * takes some bread (a message) and makes toast
     * @param message the message displayed in the Toast
     */
    private void toaster(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}