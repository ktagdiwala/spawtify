package com.example.spawtify.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.spawtify.Database.entities.Songlist;
import com.example.spawtify.Database.entities.User;

@Database(entities = {Songlist.class}, version = 1, exportSchema = false)
public abstract class SpawtifyDatabase extends RoomDatabase {
}
