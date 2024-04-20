package com.example.spawtify.Database.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/** PlaylistTest
 * Runs unit tests for Playlist entity object
 * @author James Mondragon
 * @since 04-17-2024
 */

public class PlaylistTest {
    Playlist playlist;

    @Before
    public void setUp(){
        playlist = new Playlist
                ("Music CATalog", "For cool cats only", 1);
    }

    @Test
    public void getId() {
        //  Music CATalog is the first playlist created so it should have an id of 0
        assertEquals(0, playlist.getId());
    }

    @Test
    public void setId() {
        //  Sets playlist id to 2
        playlist.setId(2);
        //  Stores playlist id
        int playlistId = playlist.getId();
        //  Checks to make sure playlist id was successfully set to 2
        assertEquals(2, playlistId);
    }

    @Test
    public void getPlaylistTitle() {
        String playlistTitle = playlist.getPlaylistTitle();
        assertEquals("Music CATalog", playlistTitle);
    }

    @Test
    public void setPlaylistTitle() {
        //  Set playlist title to "Mewsic Catalog"
        playlist.setPlaylistTitle("Mewsic CATalog");
        //  Store playlistTitle
        String playlistTitle = playlist.getPlaylistTitle();
        //  Check to make sure playlist title was successfully set to "Mewsic CATalog"
        assertEquals("Mewsic CATalog", playlistTitle);
    }

    @Test
    public void getPlaylistDescription() {
        //  Get playlistDescription using get method
        String playlistDescription = playlist.getPlaylistDescription();
        //  Ensure get method returned playlist description that we expected
        assertEquals("For cool cats only", playlistDescription);
    }

    @Test
    public void setPlaylistDescription() {
        //  Set playlist description to "Mr Meowdy's Purrsonal Playlist"
        playlist.setPlaylistDescription("Mr Meowdy's Purrsonal Playlist");
        //  Store playlist description
        String playlistDescription = playlist.getPlaylistDescription();
        //  Check if playlist description matches "Mr Meowdy's Purrsonal Playlist"
        assertEquals("Mr Meowdy's Purrsonal Playlist", playlistDescription);
    }

    @Test
    public void getSongIdString() {
        String songList = playlist.getSongIdString();
        assertEquals("Empty", songList);
    }

    @Test
    public void setSongIdString() {
        playlist.setSongIdString("\n1\n");
        assertEquals("1", playlist.getSongIdString());
    }

    @Test
    public void getUserId() {
        int userId = playlist.getUserId();
        assertEquals(1, userId);
    }

    @Test
    public void setUserId() {
        playlist.setUserId(2);
        assertEquals(2, playlist.getUserId());
    }
}