package com.example.spawtify;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.spawtify.Database.PlaylistDAO;
import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.entities.Playlist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PlaylistDAOTest {
    private PlaylistDAO playlistDAO;
    private SpawtifyDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, SpawtifyDatabase.class).build();
        playlistDAO = db.getPlaylistDAO();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void insert() {
        // Creates 2 playlists
        Playlist playlist1 = new Playlist("Chill", "Collection of lofi songs", -1);
        Playlist playlist2 = new Playlist("Pop", "Collection of pop songs", -1);
        // Inserts both playlists into the database
        playlistDAO.insert(playlist1);
        playlistDAO.insert(playlist2);
        // Creates a list to store all playlists from the database (there should be exactly 2)
        List<Playlist> playlists = playlistDAO.getAllUserPlaylists(-1);
        // Verifies that the list contains exactly 2 playlists
        assertEquals(2,playlists.size());
        // Verifies that the playlist titles of the playlists in database
        // match the playlist titles of the playlist1 and playlist2
        assertEquals(playlists.get(0).getPlaylistTitle(), playlist1.getPlaylistTitle());
        assertEquals(playlists.get(1).getPlaylistTitle(), playlist2.getPlaylistTitle());
    }

    @Test
    public void delete() {
        // Creates a playlist
        Playlist playlist1 = new Playlist("Pop", "Collection of pop songs", -1);
        // Inserts playlist1 into the DB
        playlistDAO.insert(playlist1);
        // Creates a list to store all playlists from the DB (there should be exactly 1)
        List<Playlist> playlists = playlistDAO.getAllUserPlaylists(-1);
        // Verifies that the list contains exactly 1 playlist
        assertEquals(1,playlists.size());
        // Deletes the playlist from the DB
        playlistDAO.delete(playlists.get(0));
        // Updates the list to reflect changes from DB
        playlists = playlistDAO.getAllUserPlaylists(-1);
        // Verifies that the database is now empty
        assert(playlists.isEmpty());
    }

    @Test
    public void update() {
        // Creates a playlist
        Playlist playlist1 = new Playlist("Pop", "Collection of pop songs", -1);
        // Inserts playlist1 into the DB
        playlistDAO.insert(playlist1);
        // Creates a list to store all playlists from the DB (there should be exactly 1)
        List<Playlist> playlists = playlistDAO.getAllUserPlaylists(-1);
        // Verifies that the list contains exactly 1 playlist
        assertEquals(1,playlists.size());
        // Changes the playlist title of playlist1
        playlists.get(0).setPlaylistTitle("Pop songs");
        // Passes modified playlist1 into DB to update the playlist title
        playlistDAO.update(playlists.get(0));
        // Updates the list to reflect changes from DB
        playlists = playlistDAO.getAllUserPlaylists(-1);
        // Verifies that the title of the playlist in the DB has been updated
        assertEquals(playlists.get(0).getPlaylistTitle(),"Pop songs");
    }
}
