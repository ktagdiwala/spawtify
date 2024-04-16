package com.example.spawtify.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.spawtify.Database.entities.Playlist;

import java.util.List;

/** PlaylistDAO:
 * DAO object associated with a playlist
 * @author Krishna Tagdiwala
 * @since 04-14-2024
 */

@Dao
public interface PlaylistDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Playlist playlist);

    @Delete
    void delete(Playlist playlist);

    @Query("SELECT * FROM " + SpawtifyDatabase.PLAYLIST_TABLE + " ORDER BY playlistTitle")
    List<Playlist> getAllPlaylists();

}
