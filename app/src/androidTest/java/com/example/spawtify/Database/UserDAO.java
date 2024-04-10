package com.example.spawtify.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.spawtify.Database.entities.User;

import java.util.List;


@Dao
public interface UserDAO {
    /* If we are inserting a record that has the same ID as a record that already exists,
     * the previous record will be overwritten with the new one */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    //this method will take 0 or more user objects
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + SpawtifyDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();
}
