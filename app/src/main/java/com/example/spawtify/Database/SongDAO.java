package com.example.spawtify.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.spawtify.Database.entities.Song;

import java.util.List;

/** SongDAO:
 * DAO object associated with a song
 * @author Krishna Tagdiwala
 * @since 04-09-2024
 */

@Dao
public interface SongDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Song song);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Song song);

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST)
    LiveData<List<Song>> getAllRecordsLD();

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST)
    List<Song> getAllRecords();

    @Query("DELETE FROM " + SpawtifyDatabase.SONGLIST)
    void deleteAll();

    @Query("DELETE FROM " + SpawtifyDatabase.SONGLIST + " WHERE songId = :songId")
    void deleteSongById(int songId);

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST + " WHERE songId = :songId")
    Song getSongById(int songId);

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST + " WHERE songArtist = :songArtist")
    List<Song> getSongsByArtist(String songArtist);

    @Query("SELECT DISTINCT songArtist FROM SONGLIST")
    List<String> getAllArtists();

    @Query("SELECT DISTINCT songAlbum FROM SONGLIST")
    List<String> getAllAlbums();

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST + " WHERE songAlbum = :songAlbum")
    List<Song> getSongsByAlbum(String songAlbum);

    @Query("SELECT DISTINCT songGenre FROM SONGLIST")
    List<String> getAllGenres();

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST + " WHERE songGenre = :selectedGenre")
    List<Song> getSongsByGenre(String selectedGenre);

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST + " WHERE isExplicit = false")
    List<Song> getCleanSongs();

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST + " WHERE isExplicit = true")
    List<Song> getExplicitSongs();
}
