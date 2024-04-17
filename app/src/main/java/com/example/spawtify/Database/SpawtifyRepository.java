package com.example.spawtify.Database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.spawtify.Database.entities.Playlist;
import com.example.spawtify.Database.entities.Song;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.MainActivity;

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
    private final PlaylistDAO playlistDAO;

    private LiveData<List<Song>> allSongs;
    private List<User> allUsers;
    private List<Playlist> allPlaylists;

    private static SpawtifyRepository repository;

    /** Overloaded Constructor SpawtifyRepository:
     * Overrides the default constructor
     * @param application is the application passed in
     */
    private SpawtifyRepository(Application application){
        SpawtifyDatabase db = SpawtifyDatabase.getDatabase(application);
        this.songDAO = db.getSongDAO();
        this.userDAO = db.getUserDAO();
        this.playlistDAO = db.getPlaylistDAO();

        this.allUsers = this.userDAO.getAllUsers();
        this.allSongs = this.songDAO.getAllRecordsLD();
        this.allPlaylists = this.playlistDAO.getAllPlaylists();
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

    // User-related methods
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

    public void insertUser(User... users){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(users);
        });
    }

    public void deleteUser(User user){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            userDAO.delete(user);
        });
    }

    // @Deprecated
    // /** getAllSongs:
    //  * retrieves the list of Songs from the database of songs
    //  * @return an a LiveData object containing the list of all songs in the song database
    //  */
    // public LiveData<List<Song>> getAllSongsLD(){
    //     return songDAO.getAllRecordsLD();
    // }

    // Song-related methods
    /** getAllSongs:
     * retrieves the list of Songs from the database of songs
     * @return an ArrayList of all songs in the song database
     */

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
        SpawtifyDatabase.databaseWriteExecutor.execute(()-> {
            songDAO.insert(song);
        });
    }

    public void deleteSong(Song song){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            songDAO.delete(song);
        });
    }


    public void removeSongById(int songId) {
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            songDAO.deleteSongById(songId);
        });
    }

    public void updateSong(Song song){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            songDAO.update(song);
        });
    }

    /** getSongById:
     * Retrieves song object from database with matching id
     * @param songId song ID is passed in for use by SongDAO
     * @return Song object returned by SongDAO method getSongById
     */
    public Song getSongById(int songId) {
        Future<Song> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<Song>() {
                    @Override
                    public Song call() throws Exception {
                        return songDAO.getSongById(songId);
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

    // Playlist-related methods
    /** getAllUserPlaylists:
     * @return an ArrayList of all playlists associated with a user
     * from the playlist database
     */
    public List<Playlist> getAllUserPlaylists(int currUserId){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<List<Playlist>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<Playlist>>() {
                    @Override
                    public List<Playlist> call() throws Exception {
                        return playlistDAO.getAllUserPlaylists(currUserId);
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all Playlists in the repository");
        }
        return null;
    }

    /** getAllUserPlaylistTitles:
     * @return an ArrayList of all playlist titles associated with a user
     * from the playlist database
     */
    public List<String> getAllUserPlaylistTitles(int currUserId){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<List<String>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return playlistDAO.getAllUserPlaylistTitles(currUserId);
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all Playlists in the repository");
        }
        return null;
    }

    /** getAllUserPlaylistTitles:
     * @return an ArrayList of all playlist titles associated with a user
     * from the playlist database
     */
    public List<String> getAllUserPlaylistDescriptions(int currUserId) {
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<List<String>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return playlistDAO.getAllUserPlaylistDescriptions(currUserId);
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when getting all Playlists in the repository");
        }
        return null;
    }

    public void insertPlaylist(Playlist playlist){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            playlistDAO.insert(playlist);
        });
    }

    public void deletePlaylist(Playlist playlist){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            playlistDAO.delete(playlist);
        });
    }



}
