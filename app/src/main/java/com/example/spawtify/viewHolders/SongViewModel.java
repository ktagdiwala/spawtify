package com.example.spawtify.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Song;

import java.util.List;

/** SongViewModel:
 * Responsible for preparing and managing the data for songlist recycler view
 * @author Krishna Tagdiwala
 * @since 04-13-2024
 */

public class SongViewModel extends AndroidViewModel {

    //Gets an instance of our repository
    private final SpawtifyRepository repository;


    /** SongViewModel constructor:
     * Overrides the default constructor to take in a parameter application
     * @param application is the application being passed in
     */
    public SongViewModel (Application application){
        //calls the parent object to initialize everything
        super(application);

        //Initializes the instance of repository
        repository = SpawtifyRepository.getRepository(application);

    }

    /** getAllSongs
     * @return a LiveData object (an observable data holder class)
     * which contains the list of songs
     */
    public LiveData<List<Song>> getAllSongs() {
        return repository.getAllSongsLD();
    }

    public void insert(Song song){
        repository.insertSong(song);
    }
}
