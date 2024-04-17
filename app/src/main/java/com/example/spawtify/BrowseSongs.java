package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.databinding.ActivityBrowseSongsBinding;
import com.example.spawtify.viewHolders.SongModel;
import com.example.spawtify.viewHolders.SongRecyclerViewInterface;
import com.example.spawtify.viewHolders.Song_RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BrowseSongs extends AppCompatActivity implements SongRecyclerViewInterface {
    //  Used for storing int extras to place into viewValue
    //  -1 = came from MainActivity
    //  0 = edit song
    //  1 = delete song
    private static final String VIEW_VALUE = "com.example.spawtify.VIEW_VALUE";
    private static final String FILTER_STRING = "com.example.spawtify.FILTER_STRING";
    private static final String FILTER_VALUE = "com.example.spawtify.FILTER_VALUE";

    ActivityBrowseSongsBinding binding;

    //  These values will be used in if statements to determine what activity user
    //  came from
    private final int editSong = 0;
    private final int deleteSong = 1;
    private final int mainActivity = -1;

    //  These values will be used in if statements to determine what filter will be applied
    private final int noFilters = 2;
    private final int artistFilter = 3;
    private final int albumFilter = 4;
    private final int genreFilter = 5;
    private final int explicitFilter = 6;


    //  List of SongModel objects called songModels
    //  A SongModel contains all of the same fields as a song, used by RecyclerViewAdapter
    //  to display our songs
    List<SongModel> songModels = new ArrayList<>();

    List<SongModel> filteredSongModels = new ArrayList<>();

    //  Object of SpawtifyRepository so we can interact with the database
    SpawtifyRepository spawtifyRepository;

    //  viewValue decides how to display browse songs
    private int viewValue = mainActivity;

    //  filterValues decides which filter to apply to the list of songs
    private int filterValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBrowseSongsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RecyclerView recyclerView = findViewById(R.id.songsRecyclerView);

        //  Create instance of SpawtifyRepository to use in setUpSongModels
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //  Sets up Filter Button with onClickListener
        wireUpDisplay();

        //  Set up our list of song models
        setUpSongModels();

        //  Custom method (I made it) that sets adapter based on where we came from
        //  Sets on adapter with onclick listener if we came from edit/delete song activity
        setUpAdapter(recyclerView);

        //  Sets up recyclerView with a linear layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void wireUpDisplay(){
        binding.filterButton.setOnClickListener(v -> filterView());
        binding.removeFilters.setOnClickListener(v -> removeFilter());
    }

    private void filterView(){
        Intent intent = FilterSongs.intentFactory(this);
        startActivity(intent);
    }

    /** Sets up the adapter based on the activity we came from
     * Main Activity: Default recycler view
     * Edit Song or Delete Song: Recycler View with onClickListener
     * @param recyclerView  RecyclerView view object is passed in so setAdapter can be called
     */
    private void setUpAdapter(RecyclerView recyclerView){
        //  Retrieve value that tells us which activity we came from
        viewValue = getIntent().getIntExtra(VIEW_VALUE, mainActivity);

        //  Set up default adapter (came from main activity)
        Song_RecyclerViewAdapter adapter = new Song_RecyclerViewAdapter
                (this, songModels);

        //  Create activityTitle holder, will change if we came from edit song or delete song
        String activityTitle = "Browse Songs";

        //  Set up adapter with onclick listener since we came from editSong
        if (viewValue == editSong){
            adapter = new Song_RecyclerViewAdapter
                    (this, songModels, this);
            //  Changes activityTitle to "Edit Song"
            activityTitle = "Edit Song";
        }
        //  Set up adapter with onclick listener since we came from delete song
        if (viewValue == deleteSong){
            adapter = new Song_RecyclerViewAdapter
                    (this, songModels, this);
            //  Changes activityTitle to "Delete Song"
            activityTitle = "Delete Song";
        }

        //  Display title of current activity in action bar
        Objects.requireNonNull(getSupportActionBar()).setTitle(activityTitle);

        //  Sets adapter (with/without onClickListener) for songsRecyclerView
        recyclerView.setAdapter(adapter);

    }

    /** setUpSongModels:
     *  Creates List that holds Song objects called songlist
     *  songList retrieves its Song objects from database
     *  Local song variables are created
     *  For loop iterates through songList:
     *      Sets local song variables with values retrieved from current song element from
     *      list
     *      Once all local song variables are retrieved from current song add new SongModel
     *      to list of songModels (class owned list of Song Models)
     */
    private void setUpSongModels(){
        //  Retrieve all songs from database, store them in songList in List<Song> format
        List<Song> songList = spawtifyRepository.getAllSongs();

        filterValue = getIntent().getIntExtra(FILTER_VALUE, noFilters);

        if (filterValue == artistFilter){
            String selectedArtist = getIntent().getStringExtra(FILTER_STRING);
            songList = spawtifyRepository.getSongsByArtist(selectedArtist);
        }
        if (filterValue == albumFilter){
            String selectedAlbum = getIntent().getStringExtra(FILTER_STRING);
            songList = spawtifyRepository.getSongsByAlbum(selectedAlbum);
        }
        if (filterValue == genreFilter){
            String selectedGenre = getIntent().getStringExtra(FILTER_STRING);
            songList = spawtifyRepository.getSongsByGenre(selectedGenre);
        }
        //  Song values that will be used to store information from songs
        int songId;
        String title;
        String artist;
        String album;
        String genre;
        boolean explicit;
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

    private void removeFilter(){
        //  TODO: SET UP THIS METHOD FOR REMOVING FILTERS
        toaster("You pressed remove filter");
    }

    /** For making toast, yum
     *
     * @param message   String that holds message you want to display on a buttery toast
     */
    private void toaster(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /** intentFactory:
     * Regular intentFactory for BrowseSongs for when user comes from Main Activity
     *
     * @param context   Current state of application
     * @return  Returns an intent to go to Browse Songs
     */
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, BrowseSongs.class);
        return intent;
    }

    /** intentFactory:
     *  Also takes in the viewValue to determine where user came from
     *
     * @param context   Current state of application
     * @param viewValue Int value that tells us where user came from
     * @return  Returns an intent to go to Browse Songs
     */
    public static Intent intentFactory(Context context, int viewValue){
        Intent intent = new Intent(context, BrowseSongs.class);
        intent.putExtra(VIEW_VALUE, viewValue);
        return intent;
    }

    public static Intent intentFactory(Context context, int filterValue, String filterString){
        Intent intent = new Intent(context, BrowseSongs.class);
        intent.putExtra(FILTER_VALUE, filterValue);
        intent.putExtra(FILTER_STRING, filterString);
        return intent;
    }


    //  Method inherited from SongRecyclerViewInterface
    /** onItemClick:
     *  Determines what happens when CardView row in Recycler is clicked
     *
     * @param position  Int value indicating index of current element in list that was pressed
     */
    @Override
    public void onItemClick(int position) {
        //  Local songTitle (String value) retrieved from songModel list using get(position)
        //  Position is an int value passed in that refers to the index of current element
        //  in the list
        //  Position is retrieved from getAdapterPosition method in MyViewHolder
        //  MyViewHolder inherits getAdapterPosition from RecyclerView.ViewHolder
        String songTitle = songModels.get(position).getSongTitle();
        //  Get songId (int value) using position as index passed in to get method
        int songId = songModels.get(position).getSongId();
        //  If view value == deleteSong value (0),
        //  then create dialog that displays when song is pressed to confirm deletion of
        //  pressed song
        if (viewValue == deleteSong){
            //  Set up dialog to confirm deletion of song
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            //  Sets up intial dialog message: "Delete songTitle?"
            alertBuilder.setMessage("Delete " + songTitle + "?");
            //  Sets up positive (yes) button to remove song from database and then
            //  return to AdminPerks
            alertBuilder.setPositiveButton("Yes", (dialog, which) -> {
                //  Removes song from database
                spawtifyRepository.removeSongById(songId);
                //  Sends user back to Admin Perks
                Intent intent = AdminPerks.intentFactory(this);
                startActivity(intent);
            });
            alertBuilder.setNegativeButton("No", (dialog, which) -> {});
            //  Creates and displays alert dialog to screen
            alertBuilder.create().show();
            return;
        }
        //  Sets up intent to take user to Song Edit Activity
        Intent intent = SongAddOrEdit.intentFactory(this, editSong, songId);
        startActivity(intent);
    }
}