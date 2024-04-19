package com.example.spawtify.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.User;

import java.util.List;

/** UserViewModel:
 * Responsible for preparing and managing the data for user recycler view
 * @author Krishna Tagdiwala
 * @since 04-13-2024
 */

public class UserViewModel extends AndroidViewModel {

    //Gets an instance of our repository
    private final SpawtifyRepository repository;


    /** UserViewModel constructor:
     * Overrides the default constructor to take in a parameter application
     * @param application is the application being passed in
     */
    public UserViewModel (Application application){
        //calls the parent object to initialize everything
        super(application);

        //Initializes the instance of repository
        repository = SpawtifyRepository.getRepository(application);

    }

    /** getAllUsers
     * @return a LiveData object (an observable data holder class)
     * which contains the list of users
     */
    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsersLD();
    }

    public void insert(User user){
        repository.insertUser(user);
    }
}
