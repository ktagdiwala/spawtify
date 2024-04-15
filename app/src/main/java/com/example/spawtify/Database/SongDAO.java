package com.example.spawtify.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.spawtify.Database.entities.Song;

import java.util.ArrayList;
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

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST)
    LiveData<List<Song>> getAllRecordsLD();

    @Query("SELECT * FROM " + SpawtifyDatabase.SONGLIST)
    List<Song> getAllRecords();

    @Query("DELETE FROM " + SpawtifyDatabase.SONGLIST)
    void deleteAll();
}
