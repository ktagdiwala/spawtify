package com.example.spawtify.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Playlist playlist);

    @Query("SELECT * FROM " + SpawtifyDatabase.PLAYLIST_TABLE + " WHERE userId = :currentUserId ORDER BY playlistTitle")
    List<Playlist> getAllUserPlaylists(int currentUserId);

    @Query("SELECT playlistTitle FROM " + SpawtifyDatabase.PLAYLIST_TABLE + " WHERE userId = :currentUserId ORDER BY playlistTitle")
    List<String> getAllUserPlaylistTitles(int currentUserId);

    @Query("SELECT * FROM " + SpawtifyDatabase.PLAYLIST_TABLE + " ORDER BY playlistTitle")
    List<Playlist> getAllPlaylists();

    @Query("SELECT playlistDescription FROM " + SpawtifyDatabase.PLAYLIST_TABLE + " WHERE userId = :currentUserId ORDER BY playlistTitle")
    List<String> getAllUserPlaylistDescriptions(int currentUserId);

    @Query("SELECT * FROM " + SpawtifyDatabase.PLAYLIST_TABLE + " WHERE userId = :userId AND playlistTitle = :title")
    Playlist getPlaylistByTitle(String title, int userId);

    @Query("SELECT songIdString FROM " + SpawtifyDatabase.PLAYLIST_TABLE + " WHERE userId = :userId AND playlistTitle = :title")
    String getUserPlaylistSongs(String title, int userId);

    @Query("SELECT songIdString FROM " + SpawtifyDatabase.PLAYLIST_TABLE + " WHERE userId = :userId AND playlistTitle = :title")
    LiveData<String> getUserPlaylistSongsLD(String title, int userId);
}
