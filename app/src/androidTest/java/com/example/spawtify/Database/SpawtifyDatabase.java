package com.example.spawtify.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.Database.entities.User;

/** SpawtifyDatabase
 * Contains the entities (databases) used by the application
 * @author Krishna Tagdiwala
 * @since 04-09-2024
 */

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class SpawtifyDatabase extends RoomDatabase {
    public static final String USER_TABLE = "User_Table";
}
