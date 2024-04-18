package com.example.spawtify.viewHolders;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** PlayModelTest
 * runs Unit Tests for methods in the PlaylistModel.java file
 * @author Krishna Tagdiwala
 * @since 04-17-2024
 */

public class PlaylistModelTest {

    private PlaylistModel playlistModel;


    @Before
    public void setUp() {
        playlistModel = new PlaylistModel("Japanese", "Collection of Jpop songs");
    }

    @After
    public void tearDown() {
        playlistModel = null;
    }

    @Test
    public void getPlaylistTitle() {
        assertEquals(playlistModel.getPlaylistTitle(), "Japanese");
    }

    @Test
    public void getPlaylistDescription() {
        assertEquals(playlistModel.getPlaylistDescription(), "Collection of Jpop songs");
    }
}