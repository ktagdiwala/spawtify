package com.example.spawtify.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spawtify.Database.entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class SpawtifyDatabase extends RoomDatabase {
    public static final String DB_NAME = "Spawtify_Database";
    public static final String USER_TABLE = "User_Table";

    public abstract UserDAO getUserDAO();
}
