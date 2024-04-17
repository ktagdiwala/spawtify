package com.example.spawtify.viewHolders;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SongModelTest {
    private SongModel songModel;
    private Integer songId;
    private String songTitle;
    private String songArtist;
    private String songAlbum;
    private String songGenre;
    private Boolean isExplicit;

    //  Default song fields ( COMA CAT )

    private final int defaultId = 1;
    private final String defaultTitle = "Come Cat";
    private final String defaultArtist = "Tensnake";
    private final String defaultAlbum = "Come Cat (Purple Disco Machine Re-Work";
    private final String defaultGenre = "Disco";
    private final boolean defaultExplicit = false;

    private final int newId = 9;
    private final String newTitle = "Animals";
    private final String newArtist = "Maroon 5";
    private final String newAlbum = "V";
    private final String newGenre = "Pop";
    private final boolean newExplicit = true;

    @Before
    public void setUp(){
        songModel = null;
        assertNull(songModel);

        //  Set up local values with default values
        songId = defaultId;
        songTitle = defaultTitle;
        songArtist = defaultArtist;
        songAlbum = defaultAlbum;
        songGenre = defaultGenre;
        isExplicit = defaultExplicit;
    }

    @Test
    public void testSongModel(){
        //  Test constructor
        songModel = new SongModel(songId, songTitle, songArtist, songAlbum, songGenre, isExplicit);
        assertNotNull(songModel);

        //  Assert that the created song model has same values as the default values we passed in
        assertEquals(songModel.songId, defaultId);
        assertEquals(songModel.songTitle, defaultTitle);
        assertEquals(songModel.songArtist, defaultArtist);
        assertEquals(songModel.songAlbum, defaultAlbum);
        assertEquals(songModel.songGenre, defaultGenre);
        assertEquals(songModel.isExplicit, defaultExplicit);

        //  Set songModel values to something new
        songModel.songId = newId;
        songModel.songTitle = newTitle;
        songModel.songArtist = newArtist;
        songModel.songAlbum = newAlbum;
        songModel.songGenre = newGenre;
        songModel.isExplicit = newExplicit;

        //  Test getters
        assertEquals(newId, songModel.getSongId());
        assertEquals(newTitle, songModel.getSongTitle());
        assertEquals(newArtist, songModel.getSongArtist());
        assertEquals(newAlbum, songModel.getSongAlbum());
        assertEquals(newGenre, songModel.getSongGenre());
        assertTrue(newExplicit);
    }

    @After
    public void reset(){
        songModel = null;
        assertNull(songModel);

        songId = null;
        songTitle = null;
        songArtist = null;
        songAlbum = null;
        songGenre = null;
        isExplicit = null;
    }
}