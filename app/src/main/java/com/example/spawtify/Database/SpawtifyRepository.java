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

     /** getAllUsers:
      * retrieves the list of Users from the database of users
      * @return an a LiveData object containing the list of all users in the user database
      */
     public LiveData<List<User>> getAllUsersLD(){
         return userDAO.getAllUsersLD();
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

    /** getUserById
     * Retrieves the user object based on the userId
     * @param userId is the primary key of the user object
     * @return the user object associated with the id
     */
    public User getUserById(int userId){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<User> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<User>() {
                    @Override
                    public User call() throws Exception {
                        return userDAO.getUserByUserId(userId);
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem getting user by userId from repository");
        }
        return null;
    }

    public void insertUser(User... users){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(users);
        });
    }

    public void updateUser(User user){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            userDAO.update(user);
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

    public List<Song> getSongsByArtist(String songArtist){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<List<Song>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<Song>>() {
                    @Override
                    public List<Song> call() throws Exception {
                        return songDAO.getSongsByArtist(songArtist);
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

    public List<String> getDistinctArtists(){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<List<String>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return songDAO.getAllArtists();
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

    public List<String> getDistinctAlbums() {
        Future<List<String>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return songDAO.getAllAlbums();
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

    public List<Song> getSongsByAlbum(String selectedAlbum) {
        Future<List<Song>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<Song>>() {
                    @Override
                    public List<Song> call() throws Exception {
                        return songDAO.getSongsByAlbum(selectedAlbum);
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

    public List<String> getDistinctGenres() {
        Future<List<String>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<String>>() {
                    @Override
                    public List<String> call() throws Exception {
                        return songDAO.getAllGenres();
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

    public List<Song> getSongsByGenre(String selectedGenre) {
        Future<List<Song>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<Song>>() {
                    @Override
                    public List<Song> call() throws Exception {
                        return songDAO.getSongsByGenre(selectedGenre);
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

    public List<Song> getCleanSongs() {
        Future<List<Song>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<Song>>() {
                    @Override
                    public List<Song> call() throws Exception {
                        return songDAO.getCleanSongs();
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

    public List<Song> getExplicitSongs() {
        Future<List<Song>> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<List<Song>>() {
                    @Override
                    public List<Song> call() throws Exception {
                        return songDAO.getExplicitSongs();
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

    public User getUserByUsername(String username) {
        Future<User> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<User>() {
                    @Override
                    public User call() throws Exception {
                        return userDAO.getUserByUsername(username);
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

    public void updatePlaylist(Playlist playlist){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            playlistDAO.update(playlist);
        });
    }

    public void deletePlaylist(Playlist playlist){
        SpawtifyDatabase.databaseWriteExecutor.execute(()->{
            playlistDAO.delete(playlist);
        });
    }

    public Playlist getPlaylistByTitle(String title, int userId) {
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<Playlist> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<Playlist>() {
                    @Override
                    public Playlist call() throws Exception {
                        return playlistDAO.getPlaylistByTitle(title, userId);
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

    public Song getSongByTitle(String titleToCheck) {
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<Song> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<Song>() {
                    @Override
                    public Song call() throws Exception {
                        return songDAO.getSongByTitle(titleToCheck);
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

    /** getUserPlaylistSongs:
     * @return the list of songIds in a playlist from the playlist DB
     * @param playlistTitle the title of the playlist
     * @param userId id of the current user
     */
    public String getUserPlaylistSongs(String playlistTitle, int userId){
        /* States that this will be fulfilled sometime in the future
         * Allows a thread to perform its operation
         * When it comes back, we can process it
         */
        Future<String> future = SpawtifyDatabase.databaseWriteExecutor.submit(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        return playlistDAO.getUserPlaylistSongs(playlistTitle, userId);
                    }
                }
        );
        try {
            return future.get();
        }catch (InterruptedException | ExecutionException e){
            e.printStackTrace();
            Log.i(MainActivity.TAG, "Problem when retrieving songlist from playlist");
        }
        return null;
    }

     /** getUserPlaylistSongsLD:
      * @return the livedata list of songIds in a playlist from the playlist DB
      * @param playlistTitle the title of the playlist
      * @param userId id of the current user
      */
     public LiveData<String> getUserPlaylistSongsLD(String playlistTitle, int userId){
         return playlistDAO.getUserPlaylistSongsLD(playlistTitle, userId);
     }


}
