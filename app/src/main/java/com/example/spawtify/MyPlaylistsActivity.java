package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Playlist;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivityMyPlaylistsBinding;
import com.example.spawtify.viewHolders.PlaylistModel;
import com.example.spawtify.viewHolders.PlaylistRecyclerViewInterface;
import com.example.spawtify.viewHolders.Playlist_RecyclerViewAdapter;
import java.util.ArrayList;
import java.util.List;

/** MyPlaylistActivity
 * Displays the playlists created by a user
 * Provides options to edit, create, or delete playlists
 * @author Krishna Tagdiwala
 * @since 04-15-2024
 */

public class MyPlaylistsActivity extends AppCompatActivity implements PlaylistRecyclerViewInterface {

    // allows to read information from the display
    ActivityMyPlaylistsBinding binding;

    //  Used as a key to retrieve value (int: userId) from MainActivity
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";

    // Creates a user object, initialized in MainActivity
    private User user;

    // Stores current userId
    private int userId = -1;


    // Creates SpawtifyRepository object initialized in OnCreate
    private SpawtifyRepository spawtifyRepository;

    // Stores the list of playlists in an array
    ArrayList<PlaylistModel> playlistModels = new ArrayList<>();

    // Used to determine whether the view will be in 'view playlist' or 'delete playlist' mode
    boolean deletePlaylistView = false;

    // Adapter for rendering
    Playlist_RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyPlaylistsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        // Converts extra (String) to int and assigns it to userId
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        // Checks to ensure that a valid user exists for the given userId
        if(spawtifyRepository.getUserById(userId) == null){
            toaster("Error passing userId, we have a problem.");
            Intent intent = MainActivity.intentFactory(getApplicationContext(), userId);
            startActivity(intent);
        }

        // Sets the current user object based on userId
        user = spawtifyRepository.getUserById(userId);

        // Customizes MyPlaylist title based on username
        String setTitleString =  user.getUsername() + "'s Playlists";
        binding.MyPlaylistsTextview.setText(setTitleString);

        setupPlaylistModels();
        adapter = new Playlist_RecyclerViewAdapter(this,
                spawtifyRepository.getAllUserPlaylists(userId), this);
        binding.MyPlaylistsRecyclerView.setAdapter(adapter);
        binding.MyPlaylistsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.MyPlaylistsDescriptionTextview.setText(R.string.click_on_a_playlist_to_view_contained_songs);

        // Sets up New Playlist button
        binding.NewPlaylistButton.setOnClickListener(v -> {
            Intent intent = CreatePlaylistActivity.intentFactory(getApplicationContext(), userId);
            startActivity(intent);
        });

        // Sets up Delete Playlist button
        binding.DeletePlaylistButton.setOnClickListener(v-> {
            if(!deletePlaylistView){
                binding.NewPlaylistButton.setVisibility(View.INVISIBLE);
                binding.MyPlaylistsDescriptionTextview.setText(R.string.long_press_a_playlist_to_delete_it);
                binding.DeletePlaylistButton.setText(R.string.finish);
            }else{
                binding.NewPlaylistButton.setVisibility(View.VISIBLE);
                binding.MyPlaylistsDescriptionTextview.setText(R.string.click_on_a_playlist_to_view_contained_songs);
                binding.DeletePlaylistButton.setText(R.string.delete_playlist);
            }
            deletePlaylistView = !deletePlaylistView;
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

    @Override
    public void onItemClick(int position) {

        if(deletePlaylistView) {
            //  Set up dialog to confirm deletion of song
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            //  Sets up initial dialog message: "Delete playlistTitle?"
            alertBuilder.setMessage("Delete \"" + playlistModels.get(position).getPlaylistTitle() + "\" playlist?");
            //  Sets up positive (yes) button to remove song from database and then
            //  return to AdminPerks
            alertBuilder.setPositiveButton("Yes", (dialog, which) -> {
                // Finds playlist based on title and logged in user's userId
                Playlist playlistToDelete = spawtifyRepository.getPlaylistByTitle(
                        playlistModels.get(position).getPlaylistTitle(), userId);
                //  Removes playlist from database
                spawtifyRepository.deletePlaylist(playlistToDelete);
                // Refreshes recycler display

            });
            alertBuilder.setNegativeButton("No", (dialog, which) -> {
            });
            //  Creates and displays alert dialog to screen
            alertBuilder.create().show();
            return;
        }
    }

    /** toaster
     * takes some bread (a message) and makes toast
     * @param message the message displayed in the Toast
     */
    private void toaster(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}