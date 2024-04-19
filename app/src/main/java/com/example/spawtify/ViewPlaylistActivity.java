package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Playlist;
import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.databinding.ActivityViewPlaylistBinding;
import com.example.spawtify.viewHolders.SongModel;
import com.example.spawtify.viewHolders.SongRecyclerViewInterface;
import com.example.spawtify.viewHolders.Song_RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPlaylistActivity extends AppCompatActivity implements SongRecyclerViewInterface {

    public static final String PLAYLIST_TITLE = "com.example.spawtify.PLAYLIST_TITLE";
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";

    ActivityViewPlaylistBinding binding;

    // initialized in onCreate (retrieving extra)
    private int userId;
    // initialized in onCreate (retrieving extra)
    private String playlistTitle;

    private Playlist playlist;

    // List of SongModel objects called songModels
    // A SongModel contains all of the same fields as a song,
    // used by RecyclerViewAdapter to display our songs
    List<SongModel> songModels = new ArrayList<>();

    List<Song> songList = new ArrayList<>();

    //  Object of SpawtifyRepository so we can interact with the database
    SpawtifyRepository spawtifyRepository;

    private boolean deleteSongMode = false;

    RecyclerView recyclerView;
    Song_RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPlaylistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.playlistSongsRecyclerView);
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());
        // Converts extra (String) to int and assigns it to userId
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);
        // Initializes playlistTitle to the playlist title of the clicked playlist obj
        playlistTitle = getIntent().getStringExtra(PLAYLIST_TITLE);
        playlist = spawtifyRepository.getPlaylistByTitle(playlistTitle, userId);

        if(playlist.getSongIdString().equals("\n")){
            toaster("Your playlist CATalog is empty");
        }

        // sets up title and description display
        binding.currPlaylistTitleTextview.setText(playlistTitle);
        binding.currPlaylistDescriptionTextview.setText(playlist.getPlaylistDescription());

        // sets up songModels for recycler view
        setupSongModels(playlistTitle, userId);

        // attaches the songAdapter adapter to this view
        adapter = new Song_RecyclerViewAdapter(this, songModels, this);
        binding.playlistSongsRecyclerView.setAdapter(adapter);
        binding.playlistSongsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        wireUpDisplay();

    }

    private void wireUpDisplay() {
        binding.addSongsPlaylistButton.setOnClickListener(v -> addSongs());
        binding.deleteSongsPlaylistButton.setOnClickListener(v -> deleteSongs());
    }

    private void addSongs() {
        if(playlistTitle.equals("Purrsonal Playlist")){
            toaster("You cannot modify a default playlist");
        }else {
            Intent intent = BrowseSongs.intentFactory(getApplicationContext(), 7, userId, playlistTitle);
            startActivity(intent);
        }
    }

    private void deleteSongs() {
        if (playlistTitle.equals("Purrsonal Playlist")){
            toaster("You cannot modify a default playlist");
        }else {
            if (!deleteSongMode) {
                if(playlist.getSongIdString().equals("\n")){
                    toaster("There are no songs to delete.");
                }else {
                    binding.addSongsPlaylistButton.setVisibility(View.INVISIBLE);
                    binding.deleteSongsPlaylistButton.setText(R.string.finish);
                    deleteSongMode = true;
                }
            } else {
                binding.addSongsPlaylistButton.setVisibility(View.VISIBLE);
                binding.deleteSongsPlaylistButton.setText(R.string.delete_songs);
                deleteSongMode = false;
                Intent intent = ViewPlaylistActivity.intentFactory(getApplicationContext(), playlistTitle, userId);
                startActivity(intent);
            }
        }
    }

    /** setupSongModels:
     *  sets up the individual items in the recycler view list
     * @param playlistTitle the title of the playlist whose songs are being retrieved
     * @param userId the id of the currently logged in user
     */
    private void setupSongModels(String playlistTitle, int userId){
        //  Retrieves playlist's songIdString
        String songIdString = (spawtifyRepository.getUserPlaylistSongs(playlistTitle, userId));
        try{
//            String songIdString = songIdStringLD.getValue();
            if (songIdString != null){
                // Splits the string into individual strings
                // each string contains a single songId
                String[] songIds = songIdString.split("\n");

                // parses song IDs in the songIds array to integers
                // retrieves Songs by songId and adds them to songList arraylist
                for (String songId : songIds){
                    if(!songId.trim().isEmpty()){
                        int songIdInt = Integer.parseInt(songId);
                        songList.add(spawtifyRepository.getSongById(songIdInt));
                    }
                }

                //  Song values that will be used to store information from songs
                int songId;
                String title;
                String artist;
                String album;
                String genre;
                boolean explicit;

                //  Making sure songModels is empty before repopulating (important for removing filters)
                if (!songModels.isEmpty()){
                    songModels.clear();
                }

                //  For loop that iterates through songList
                //  Method song values are set using Song POJO getters
                //  New SongModel with current song values is sent into the list named songModels
                for (int i = 0; i < songList.size(); i++){
                    songId = songList.get(i).getSongId();
                    title = songList.get(i).getSongTitle();
                    artist = songList.get(i).getSongArtist();
                    album = songList.get(i).getSongAlbum();
                    genre = songList.get(i).getSongGenre();
                    explicit = songList.get(i).isExplicit();
                    //  songModels (List<SongModels> gets songs added one by one as we go through
                    //  songList
                    songModels.add(i,new SongModel
                            (songId, title, artist, album, genre, explicit));
                }
            }else {
                toaster(playlistTitle + " does not contain any songs yet.");
            }
        }catch (Exception e){
            toaster("Error converting livedata object");
        }

    }

    @Override
    public void onItemClick(int position) {
        int songId = songModels.get(position).songId;
        if(deleteSongMode){
            if (playlist.getSongIdString().contains("\n" + songId + "\n")) {
                playlist.setSongIdString(playlist.getSongIdString().replace("\n" + songId + "\n", ""));
                spawtifyRepository.updatePlaylist(playlist);
                toaster(songModels.get(position).songTitle + " has been removed from " + playlistTitle);
            }else{
                toaster("This song has already been removed, click finish to refresh");
            }
        }
    }

    public static Intent intentFactory(Context context, String playlistTitle, int userId) {
        Intent intent = new Intent(context, ViewPlaylistActivity.class);
        intent.putExtra(PLAYLIST_TITLE, playlistTitle);
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