package com.example.spawtify.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/** SpawtifyRepository:
 * DAO object associated with a user
 * @author Krishna Tagdiwala
 * @since 04-09-2024
 */

public class SpawtifyRepository {

    private final SongDAO songDAO;
    private final UserDAO userDAO;
    private List<Song> allSongs;
    private List<User> allUsers;

    private static SpawtifyRepository repository;

    /** Overloaded Constructor SpawtifyRepository:
     * Overrides the default constructor
     * @param application is the application passed in
     */
    private SpawtifyRepository(Application application){
        SpawtifyDatabase db = SpawtifyDatabase.getDatabase(application);
        this.songDAO = db.getSongDAO();
        this.userDAO = db.getUserDAO();

        this.allUsers = this.userDAO.getAllUsers();
        this.allSongs = this.songDAO.getAllRecords();
    }

    public static SpawtifyRepository getRepository(Application application){
        if (repository != null){
            return repository;
        }
        Future<SpawtifyRepository> future;
        future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<SpawtifyRepository>() {
                    @Override
                    public SpawtifyRepository call() throws Exception {
                        return new SpawtifyRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting Spawtify Repository, thread error");
        }
        return null;
    }

    public List<User> getAllUsers(){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<List<User>> future;
        future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<User>>() {
                    @Override
                    public List<User> call() throws Exception {
                        return userDAO.getAllUsers();
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all Songs in the repository");
        }
        return null;
    }

    /** getAllSongs:
     * retrieves the list of Songs from the database of songs
     * @return an a LiveData object containing the list of all songs in the song database
     */
    public LiveData<List<Song>> getAllSongsLD(){
        return songDAO.getAllRecordsLD();
    }

    /** getAllSongs:
     * retrieves the list of Songs from the database of songs
     * @return an ArrayList of all songs in the song database
     */
    @Deprecated
    public List<Song> getAllSongs(){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<List<Song>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<Song>>() {
                    @Override
                    public List<Song> call() throws Exception {
                        return songDAO.getAllRecords();
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all Songs in the repository");
        }
        return null;
    }

    public void insertSong(Song song){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->
        {
            songDAO.insert(song);
        });
    }

    public void insertUser(User... users){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(users);
        });
    }
}
