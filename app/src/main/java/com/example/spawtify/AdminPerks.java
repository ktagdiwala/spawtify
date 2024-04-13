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
    }

    private void newSong(){
        Intent intent = SongAddOrEdit.intentFactory(this, 1);
        startActivity(intent);
    }

    private void editSong(){
        Intent intent = SongAddOrEdit.intentFactory(this, 0);
        startActivity(intent);
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, AdminPerks.class);
        return intent;
    }
}