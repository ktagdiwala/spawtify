package com.example.spawtify.Database;

import android.app.Application;

import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.Database.entities.User;

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

    private SongDAO songDAO;
    private ArrayList<Song> allSongs;

    private UserDAO userDAO;
    private ArrayList<User> allUsers;

    /** Overloaded Constructor SpawtifyRepository:
     * Overrides the default constructor
     * @param application is the application passed in
     */
    public SpawtifyRepository(Application application){
        SpawtifyDatabase db = SpawtifyDatabase.getDatabase(application);
        this.songDAO = db.getSongDAO();
//        this.allSongs = this.songDAO.getAllRecords();
        //  TODO: uncomment this out later
    }

    public ArrayList<User> getAllUsers(){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<ArrayList<User>> future;
        future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<User>>() {
                    @Override
                    public ArrayList<User> call() throws Exception {
//                        return userDAO.getAllUsers();
                        return allUsers;
                        //  TODO: uncomment original return
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
//            Log.i(MainActivity.TAG, "Problem when getting all Songs in the repository");
        }
        return null;
    }

    /** getAllSongs:
     * retrieves the list of Songs from the database of songs
     * @return an ArrayList of all songs in the song database
     */
    public ArrayList<Song> getAllSongs(){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<ArrayList<Song>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<Song>>() {
                    @Override
                    public ArrayList<Song> call() throws Exception {
//                        return songDAO.getAllRecords();
                        //  TODO: uncomment this out later
                        return allSongs;
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
//            Log.i(MainActivity.TAG, "Problem when getting all Songs in the repository");
        }
        return null;
    }

    public void insertSong(Song song){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->
        {
            songDAO.insert(song);
        });
    }
}
