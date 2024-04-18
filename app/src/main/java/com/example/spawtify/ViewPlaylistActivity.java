package com.example.spawtify;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.databinding.ActivityViewPlaylistBinding;
import com.example.spawtify.viewHolders.SongModel;
import com.example.spawtify.viewHolders.SongRecyclerViewInterface;
import com.example.spawtify.viewHolders.Song_RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPlaylistActivity extends AppCompatActivity implements SongRecyclerViewInterface {

    public static final String PLAYLIST_TITLE = "com.example.spawtify.PLAYLIST_TITLE";

    ActivityViewPlaylistBinding binding;

    // List of SongModel objects called songModels
    // A SongModel contains all of the same fields as a song,
    // used by RecyclerViewAdapter to display our songs
    List<SongModel> songModels = new ArrayList<>();

    List<Song> songList = new ArrayList<>();

    //  Object of SpawtifyRepository so we can interact with the database
    SpawtifyRepository spawtifyRepository;

    RecyclerView recyclerView;
    Song_RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPlaylistBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.playlistSongsRecyclerView);
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //TODO: Set up recycler view in ViewPlaylist activity
    }

    /** setUpSongModels:
     *  Creates List that holds Song strings called songlist
     *  songlist retrieves song strings from database
     *  For loop iterates through songList:
     *      Sets local song variables with values retrieved from current song element from
     *      list
     *      Once all local song variables are retrieved from current song add new SongModel
     *      to list of songModels (class owned list of Song Models)
     */
    private void setupSongModels(){
        //  Retrieve all songs from database, store them in songList in List<Song> format
        songList = spawtifyRepository.getAllSongs();

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
    }

    @Override
    public void onItemClick(int position) {

    }
}