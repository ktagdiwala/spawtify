package com.example.spawtify.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.Database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** SpawtifyDatabase:
 * Contains the entities (databases) used by the application
 * @author Krishna Tagdiwala
 * @since 04-09-2024
 */

@Database(entities = {User.class, Song.class}, version = 2, exportSchema = false)
public abstract class SpawtifyDatabase extends RoomDatabase {

    private static final String DB_NAME = "Spawtify_Database";
    //Database of users
    public static final String USER_TABLE = "User_Table";
    //Database of songs
    public static final String SONGLIST = "Songlist";

    //Instance only ever exists in RAM -- helps prevent conflicts and allows database to work well
    private static volatile SpawtifyDatabase INSTANCE;

    /* Avoid running database queries on main thread
     * Prevent multiple things to access the database at any given time */
    private static final int NUMBER_OF_THREADS = 4;

    /* Create a service that will supply threads for us to do DB operations
     * create them all at startup, put them in a pool
     * as we need to do DB operations, pull threads out of the pool
     * database will have a maximum of 4 threads for efficiency */
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static SpawtifyDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (SpawtifyDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    SpawtifyDatabase.class,
                                    DB_NAME
                            )
                            //if any modifications are made to this DB class, wipe the current database and start over
                            .fallbackToDestructiveMigration()
                            //CallBack is code that we are going to run. This line allows us to add default values
                            .addCallback(addDefaultValues)
                            .build();
                }

            }
        }
        return INSTANCE;

    }

    /** addDefaultValues:
     * an anonymous inner class of type RoomDatabase.Callback
     */
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
//            Log.i(MainActivity.TAG, "DATABASE CREATED");
            //Used as a way to insert default records into the database
            //Is a lambda (anonymous function)
            //TODO: add databaseWriteExecutor.execute(() -> {...}
        }
    };


    public abstract SongDAO getSongDAO();

    public abstract UserDAO getUserDAO();
}
