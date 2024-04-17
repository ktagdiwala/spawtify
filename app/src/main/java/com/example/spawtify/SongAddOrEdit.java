package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.databinding.ActivitySongAddOrEditBinding;

/** SongAddOrEdit:
 *  View that changes displayed text based on where the user came from
 *  1) New Song View, add song to song list
 *  2) Edit Song View, edit song selected from song list
 *
 * @author James Mondragon
 * @since 04-15-2024
 */

public class SongAddOrEdit extends AppCompatActivity {
    //  Binding for manipulating display objects
    ActivitySongAddOrEditBinding binding;

    //  Key values used for storing int extra values
    private static final String ADD_OR_EDIT = "com.example.spawtify.addOrEdit";
    private static final String SONG_ID = "com.example.spawtify.SONG_ID";

    //  1 = user came from pressing "New Song" button
    private final int addSong = 1;
    //  0 = user came from pressing "Edit Song" button
    private final int editSong = 0;

    //  Int where we store ADD_OR_EDIT value
    //  Tells us where the user came from
    private int addOrEdit;

    //  Initialized in createDuplicateSong (Only called when coming from Edit Button)
    //  A song object that represents the song the user selected to edit
    private Song selectedSongToEdit;

    //  Fields for song values
    private String title;
    private String artist;
    private String album;
    private String genre;
    private boolean explicit;

    //  Initialized in onCreate, used in methods to insert or edit a song
    SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySongAddOrEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //  Initialized for use in reading/writing to database
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //  Stores passed in integer to addOrEdit, determines whether new song page is displayed
        //  or edit song page
        addOrEdit = getIntent().getIntExtra(ADD_OR_EDIT, -1);

        //  Sets up Explicit Switch text
        //  Checked: Displays "Explicit"
        //  Unchecked: Displays "Not Explicit"
        setUpExplicitSwitch();

        //  If user came from pressing "Edit Song" button, then call setUpEditSongView
        if (addOrEdit == editSong){
            setUpEditSongView();
        }
        //  If user came from pressing "New Song" button, then call setUpNewSongView
        if (addOrEdit == addSong){
            setUpNewSongView();
        }

        //  Adds onClickListener to AddOrChangeButton
        //  Changes functionality of button depending on where user came from
        wireUpDisplay();
    }

    /** setUpExplicitSwitch:
     *  Sets up Explicit Switch text
     *  Checked: Displays "Explicit"
     *  Unchecked: Displays "Not Explicit"
     */
    private void setUpExplicitSwitch() {
        binding.explicitSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //  If switch is checked (on), then set text to "Explicit"
            if (isChecked){
                binding.explicitSwitch.setText(R.string.explicit);
            }
            //  If switch is not checked (off), then set text to "Not Explicit"
            if (!isChecked){
                binding.explicitSwitch.setText(R.string.not_explicit);
            }
        });
    }

    /** wireUpDisplay:
     *  Sets up AddOrChangeButton with onClickListener
     *  If we came from pressing new song, then insert song to database
     *  If we came from pressing edit song, then update song in database
     */
    private void wireUpDisplay(){
        binding.AddOrChangeButton.setOnClickListener(v -> {
            //  Retrieve values from display, store in local variables
            if (!getValuesFromDisplay()){
                return;
            }
            //  Create local song object with local variables
            Song song = new Song(title, artist, album, genre, explicit);
            //  Setting up intent to return to Admin Perks Activity
            Intent intent = AdminPerks.intentFactory(this);
            //  If we came from pressing "New Song" button, then insert song to database
            if (addOrEdit == addSong){
                //  Add song to database
                spawtifyRepository.insertSong(song);
                //  Not sure if this still displays, maybe sent to admin perks before this
                //  has a chance to show up
                toaster("You've added " + title);
                //  Return user to Admin Perks Activity
                startActivity(intent);
                return;
            }
            //  Not sure if this still displays, maybe sent to admin perks before this
            //  has a chance to show up
            toaster("You've edited " + title);
            //  Set local song object with same songId as selectedSongToEdit
            //  Ensures updateSong works as intended
            song.setSongId(selectedSongToEdit.getSongId());
            //  Edits song in database
            spawtifyRepository.updateSong(song);
            //  Returns user to Admin Perks Activity
            startActivity(intent);
        });
    }

    /** setUpNewSongView:
     *  Sets up display for New Song View
     *  Title: New Song
     *  Button: Add Song
     */
    private void setUpNewSongView(){
        //  Set text for new song view title
        binding.NewSongOrEditSongTitle.setText(R.string.new_song);
        //  Set text for new song view button
        binding.AddOrChangeButton.setText(R.string.add_song);
    }

    /** setUpEditSongView:
     *  Sets up display for New Song View
     *  Title: Edit Song
     *  Button: Save Changes
     *  ---------------------------------
     *  Auto-populates song fields with selected song fields
     */
    private void setUpEditSongView(){
        //  Set text for edit song view title
        binding.NewSongOrEditSongTitle.setText(R.string.edit_song);
        //  Set text for edit song view button
        binding.AddOrChangeButton.setText(R.string.save_changes);
        //  Creates copy of song that user selected from song list
        createDuplicateSong();
        //  Set up local variables with values from chosen song
        title = selectedSongToEdit.getSongTitle();
        artist = selectedSongToEdit.getSongArtist();
        album = selectedSongToEdit.getSongAlbum();
        genre = selectedSongToEdit.getSongGenre();
        explicit = selectedSongToEdit.isExplicit();
        //  Set text for song fields
        binding.EnterTitle.setText(title);
        binding.EnterArtist.setText(artist);
        binding.EnterAlbum.setText(album);
        binding.EnterGenre.setText(genre);
        binding.explicitSwitch.setChecked(explicit);
    }

    /** createDuplicateSong:
     *  Initializes songId with passed in SONG_ID value
     *  If (for some weird reason) we got here without a stored SONG_ID value,
     *  then display error message and return
     */
    private void createDuplicateSong(){
        //  Retrieves passed in SONG_ID value (int extra)
        int songId = getIntent().getIntExtra(SONG_ID, -1);
        //  If song ID == -1 (default value) something went terribly wrong
        if (songId == -1){
            toaster("SONG_ID not found, SONG_ID passed in from Browse Songs");
            return;
        }
        //  Retrieve song by id from database
        selectedSongToEdit = spawtifyRepository.getSongById(songId);
    }

    private void toaster(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /** getValuesFromDisplay:
     * Retrieves values from display:
     * title, artist, album, genre, explicit
     */
    private boolean getValuesFromDisplay(){
        title = binding.EnterTitle.getText().toString();
        artist = binding.EnterArtist.getText().toString();
        album = binding.EnterAlbum.getText().toString();
        genre = binding.EnterGenre.getText().toString();
        explicit = binding.explicitSwitch.isChecked();

        if (title.isEmpty() || title.isBlank() ||
            artist.isEmpty() || artist.isBlank() ||
            album.isEmpty() || album.isBlank() ||
            genre.isEmpty() || genre.isBlank())
        {
                toaster("One or more of your fields are empty!");
                return false;
        }
        return true;
    }

    public static Intent intentFactory(Context context, int addOrEdit){
        Intent intent = new Intent(context, SongAddOrEdit.class);
        intent.putExtra(ADD_OR_EDIT, addOrEdit);
        return intent;
    }

    public static Intent intentFactory
            (Context context, int addOrEdit, int songId){
        Intent intent = new Intent(context, SongAddOrEdit.class);
        intent.putExtra(ADD_OR_EDIT, addOrEdit);
        intent.putExtra(SONG_ID, songId);
        return intent;
    }
}