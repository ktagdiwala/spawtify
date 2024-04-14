package com.example.spawtify.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.Song;

import java.util.List;

/** SongViewModel:
 * TODO: add description
 * @author Krishna Tagdiwala
 * @since 04-13-2024
 */

public class SongViewModel extends AndroidViewModel {

    //Gets an instance of our repository
    private final SpawtifyRepository repository;

    //Creates a LiveData object (an observable data holder class)
    //Contains the list of the songs
    private final LiveData<List<Song>> allSongsById;

    /** SongViewModel constructor
     * Overrides the default constructor to take in a parameter application
     * @param application is the application being passed in
     */
    public SongViewModel (Application application){
        //calls the parent object to initialize everything
        super(application);

        //Initializes the instance of repository
        repository = SpawtifyRepository.getRepository(application);

        //Returns a LiveData object containing the list of songs
        allSongsById = repository.getAllSongsLD();
    }

    public LiveData<List<Song>> getAllSongsById() {
        return allSongsById;
    }
}
