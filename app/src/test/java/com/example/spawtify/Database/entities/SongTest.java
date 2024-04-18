package com.example.spawtify.Database.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/** SongTest
 * Runs Unit tests for Song entity object
 * @author Krishna Tagdiwala
 * @since 04-17-2024
 */

public class SongTest {

    Song song;

    @Before
    public void setUp() {
        song = new Song("Virtual Insanity", "Jamiroquai", "Single",
                "Pop", false);
    }

    @After
    public void tearDown() {
        song = null;
    }

    @Test
    public void getSongTitle() {
        assertEquals("Virtual Insanity", song.getSongTitle());
    }

    @Test
    public void setSongTitle() {
        assertNotEquals("Ride", song.getSongTitle());
        song.setSongTitle("Ride");
        assertEquals("Ride", song.getSongTitle());
    }

    @Test
    public void getSongArtist() {
        assertEquals("Jamiroquai", song.getSongArtist());
    }

    @Test
    public void setSongArtist() {
        assertNotEquals("Twenty One Pilots", song.getSongArtist());
        song.setSongArtist("Twenty One Pilots");
        assertEquals("Twenty One Pilots", song.getSongArtist());
    }

    @Test
    public void getSongAlbum() {
        assertEquals("Single", song.getSongAlbum());
    }

    @Test
    public void setSongAlbum() {
        assertNotEquals("Blurryface", song.getSongAlbum());
        song.setSongAlbum("Blurryface");
        assertEquals("Blurryface", song.getSongAlbum());
    }

    @Test
    public void getSongGenre() {
        assertEquals("Pop", song.getSongGenre());
    }

    @Test
    public void setSongGenre() {
        assertNotEquals("Alternative rock", song.getSongGenre());
        song.setSongGenre("Alternative rock");
        assertEquals("Alternative rock", song.getSongGenre());
    }

    @Test
    public void isExplicit() {
        assertFalse(song.isExplicit());
    }

    @Test
    public void setExplicit() {
        assertFalse(song.isExplicit());
        song.setExplicit(true);
        assertTrue(song.isExplicit());
    }
}