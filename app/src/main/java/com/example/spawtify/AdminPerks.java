package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spawtify.databinding.ActivityAdminPerksBinding;

public class AdminPerks extends AppCompatActivity {

    ActivityAdminPerksBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminPerksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        wireUpDisplay();
    }

    private void wireUpDisplay(){
        binding.NewSongButton.setOnClickListener(v -> newSong());
        binding.EditSongButton.setOnClickListener(v -> editSong());
        binding.DeleteSongButton.setOnClickListener(v -> deleteSong());
        binding.ManageUsersButton.setOnClickListener(v -> {
                Intent intent = ManageUsersActivity.intentFactory(getApplicationContext());
                startActivity(intent);
        });
    }

    private void deleteSong() {
        int deleteSongValue = 1;
        Intent intent = BrowseSongs.intentFactory(this, deleteSongValue);
        startActivity(intent);
    }

    private void newSong(){
        int newSongValue = 1;
        Intent intent = SongAddOrEdit.intentFactory(this, newSongValue);
        startActivity(intent);
    }

    private void editSong(){
        int editSongValue = 0;
        Intent intent = BrowseSongs.intentFactory(this, editSongValue);
        startActivity(intent);
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AdminPerks.class);
        return intent;
    }
}