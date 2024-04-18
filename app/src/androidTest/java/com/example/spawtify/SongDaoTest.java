package com.example.spawtify;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.spawtify.Database.SongDAO;
import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.entities.Song;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class SongDaoTest {
    private SongDAO songDAO;
    private SpawtifyDatabase db;



    @Before
    public void createDatabase(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, SpawtifyDatabase.class).build();
        songDAO = db.getSongDAO();
    }

    @After
    public void closeDb(){
        db.close();
    }

    @Test
    public void insertTest(){
        //  Creates song for us to pass into database
        Song testSong = new Song
                        ("From the Start", "Good Kid",
                        "From the Start", "Rock", false);
        //  Inserts our testSong into the database
        songDAO.insert(testSong);
        //  Retrieves all songs in database (there should only be the one we inserted)
        List<Song> allSongs = songDAO.getAllRecords();
        //  Verifies this is the only song in the allSongs list
        assertEquals(allSongs.size(),1);
        //  Verifies the only song in allSongs is the same as the song we inserted into the database
        assertEquals(allSongs.get(0).getSongTitle(), testSong.getSongTitle());

    }

    @Test
    public void updateTest(){
        //  Creates song for us to pass into database
        Song testSong = new Song
                        ("From the Start", "Good Kid",
                        "From the Start", "Rock", false);
        songDAO.insert(testSong);
        //  Retrieves all songs from database (should be only the one we passed in
        List<Song> allSongs = songDAO.getAllRecords();
        //  Verifies this is the only song in the allSongs list
        assertEquals(allSongs.size(),1);
        //  Verifies the only song in allSongs is the same as the song we inserted into the database
        assertEquals(allSongs.get(0).getSongTitle(), testSong.getSongTitle());
        //  Update local song title
        allSongs.get(0).setSongTitle("To the End");
        //  Update song in database
        songDAO.update(allSongs.get(0));
        //  update our list of songs from database (only one song, the one we sent in)
        allSongs = songDAO.getAllRecords();
        //  Assert "To the End" is equal to songTitle contained in Song object located in 0th index
        //  of allSongs
        assertEquals("To the End", allSongs.get(0).getSongTitle());
        //  Deletes song that we sent into the database
        //  **DELETE SONG BY SENDING IN TESTSONG DOES NOT WORK BECAUSE THE IDS ARE DIFFERENT
        songDAO.deleteSongById(allSongs.get(0).getSongId());
        //  Retrieves songs from database
        //  (there should be none inside since we deleted the only one
        allSongs = songDAO.getAllRecords();
        //  Asserts that allSongs size is equal to 0, since we deleted the only song in the database
        assertEquals(0, allSongs.size());
    }

    @Test
    public void deleteTest(){
        //  Creates song for us to pass into database
        Song testSong = new Song
                ("From the Start", "Good Kid",
                        "From the Start", "Rock", false);
        songDAO.insert(testSong);
        //  Retrieves all songs from database (should be only the one we passed in
        List<Song> allSongs = songDAO.getAllRecords();
        //  Verifies this is the only song in the allSongs list
        assertEquals(1,allSongs.size());
        //  Verifies the only song in allSongs is the same as the song we inserted into the database
        assertEquals(allSongs.get(0).getSongTitle(), testSong.getSongTitle());
        //  Deletes song that we sent into the database
        //  **DELETE SONG BY SENDING IN TESTSONG DOES NOT WORK BECAUSE THE IDS ARE DIFFERENT
        songDAO.deleteSongById(allSongs.get(0).getSongId());
        //  Retrieves songs from database
        //  (there should be none inside since we deleted the only one
        allSongs = songDAO.getAllRecords();
        //  Asserts that allSongs size is equal to 0, since we deleted the only song in the database
        assertEquals(0, allSongs.size());
    }
}
