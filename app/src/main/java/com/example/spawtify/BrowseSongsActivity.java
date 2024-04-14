package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BrowseSongsActivity extends AppCompatActivity {

    /** BrowseSongsActivity:
     * Display for user to view the current song list
     * User can also filter songs by artist/album/genre/explicit
     * @author Krishna Tagdiwala
     * @since 04-13-2024
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_browse_songs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, BrowseSongsActivity.class);
        return intent;
    }

}