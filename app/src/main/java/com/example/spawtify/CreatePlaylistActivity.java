package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Playlist;
import com.example.spawtify.databinding.ActivityCreatePlaylistBinding;


public class CreatePlaylistActivity extends AppCompatActivity {

    // Used as a key to retrieve value (int: userId) from MainActivity
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";

    // Stores current userId
    int loggedInUserId = -1;

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

        // converts the passed in string to an integer to store the userId
//        loggedInUserId = Integer.parseInt(USER_ID_KEY);
//
        // sets up Create button
        binding.FinishCreatePlaylistButton.setOnClickListener(v -> {
            // sets the playlist title to the text input
            playlistTitle = binding.PlaylistTitleEditText.getText().toString();
            // sets the playlist description to the text input
            playlistDescription = binding.PlaylistDescriptionEditText.getText().toString();

//            // creates new playlist if requirements are met and returns to the MyPlaylistsActivity view
            if(checkRequirements(playlistTitle, playlistDescription)){
                Playlist newPlaylist = new Playlist(playlistTitle, playlistDescription, loggedInUserId);
                spawtifyRepository.insertPlaylist(newPlaylist);
//
                Intent intent = MyPlaylistsActivity.intentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
            }
//
        });
    }

    /** checkRequirements:
     * Checks if title field is empty
     * Checks if description field is empty
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
        toaster("\"" + title + "\" playlist has been created.");
        return true;
    }

    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, CreatePlaylistActivity.class);
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