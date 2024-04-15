package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.spawtify.Database.SongDAO;
import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.databinding.ActivitySongAddOrEditBinding;

public class SongAddOrEdit extends AppCompatActivity {

    ActivitySongAddOrEditBinding binding;

    private static final String ADD_OR_EDIT = "com.example.spawtify.addOrEdit";
    private static final String SONG_ID = "com.example.spawtify.SONG_ID";
    private final int addSong = 1;
    private final int editSong = 0;
    private int addOrEdit;
    private Song selectedSongToEdit;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private boolean explicit;
    SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySongAddOrEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //  Stores passed in integer to addOrEdit, determines whether new song page is displayed
        //  or edit song page
        addOrEdit = getIntent().getIntExtra(ADD_OR_EDIT, 0);

        if (addOrEdit == editSong){
            setUpEditSongView();
        }
        if (addOrEdit == addSong){
            setUpNewSongView();
        }

        //  Sets up button actions
        wireUpDisplay();
    }

    private void setUpNewSongView(){
        binding.NewSongOrEditSongTitle.setText(R.string.new_song);
        binding.AddOrChangeButton.setText(R.string.add_song);
    }

    private void setUpEditSongView(){
        //  Sets up switch, displays explicit when checked, not explicit when not checked
        binding.explicitSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                binding.explicitSwitch.setText(R.string.explicit);
            }
            if (!isChecked){
                binding.explicitSwitch.setText(R.string.not_explicit);
            }
        });
        //  If addOrEdit != 1, set title and button up for Edit Song
        if(addOrEdit == editSong){
            binding.NewSongOrEditSongTitle.setText(R.string.edit_song);
            binding.AddOrChangeButton.setText(R.string.save_changes);
        }

        // If addOrEdit == 1, set title and button up for New Song
        if (addOrEdit == addSong){
        }
    }

    private void wireUpDisplay(){
        binding.AddOrChangeButton.setOnClickListener(v -> {
            //  Retrieve values from display, store in local variables
            getValuesFromDisplay();

            //  Create local song object with local variables
            Song song = new Song(title, artist, album, genre, explicit);

            if (addOrEdit == addSong){
                spawtifyRepository.insertSong(song);
            }
            if (addOrEdit == editSong){
                spawtifyRepository.updateSong(song);
            }

            Intent intent = AdminPerks.intentFactory(getApplicationContext());
            startActivity(intent);
        });
    }

    /** getValuesFromDisplay:
     * Retrieves values from display:
     * title, artist, album, genre, explicit
     */
    private void getValuesFromDisplay(){
        title = binding.EnterTitle.getText().toString();
        artist = binding.EnterArtist.getText().toString();
        album = binding.EnterAlbum.getText().toString();
        genre = binding.EnterGenre.getText().toString();
        explicit = binding.explicitSwitch.isChecked();
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