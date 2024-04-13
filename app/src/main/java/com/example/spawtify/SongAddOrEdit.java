package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.spawtify.Database.SongDAO;
import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.databinding.ActivitySongAddOrEditBinding;

public class SongAddOrEdit extends AppCompatActivity {

    ActivitySongAddOrEditBinding binding;

    private static final String ADD_OR_EDIT = "com.example.spawtify.addOrEdit";
    private int addOrEdit;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private boolean explicit;
    SongDAO songDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySongAddOrEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getDatabase();

        //  Stores passed in integer to addOrEdit, determines whether new song page is displayed
        //  or edit song page
        addOrEdit = getIntent().getIntExtra(ADD_OR_EDIT, 0);

        //  Sets up display for user
        wireUpDisplay();
    }

    private void getDatabase(){
        songDao = Room.databaseBuilder
                        (this, SpawtifyDatabase.class, SpawtifyDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getSongDAO();
    }

    private void wireUpDisplay(){
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
        int add = 1;
        if(addOrEdit != add){
            binding.NewSongOrEditSongTitle.setText(R.string.edit_song);
            binding.AddOrChangeButton.setText(R.string.save_changes);
        }

        // If addOrEdit == 1, set title and button up for New Song
        if (addOrEdit == add){
            binding.NewSongOrEditSongTitle.setText(R.string.new_song);
            binding.AddOrChangeButton.setText(R.string.add_song);
        }

        binding.AddOrChangeButton.setOnClickListener(v -> {
            //  Retrieve values from display, store in local variables
            getValuesFromDisplay();

            //  Create local song object with local variables
            Song song = new Song(title, artist, album, genre, explicit);

            songDao.insert(song);

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
}