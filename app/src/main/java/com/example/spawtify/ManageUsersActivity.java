package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivityManageUsersBinding;
import com.example.spawtify.viewHolders.UserAdapter;
import com.example.spawtify.viewHolders.UserViewModel;

/** ManageUsersActivity:
 * Display for admin to view the current user list (non-admins)
 * Admin can delete users or upgrade a user to admin
 * @author Krishna Tagdiwala
 * @since 04-13-2024
 */

public class ManageUsersActivity extends AppCompatActivity {

    private ActivityManageUsersBinding binding;
    private SpawtifyRepository repository;
    private UserViewModel userViewModel;
    
    private int currentMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Reference to the the songViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        //Adds recycler view
        RecyclerView recyclerView = binding.UserlistRecyclerView;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = SpawtifyRepository.getRepository(getApplication());

        //adding an observer
        userViewModel.getAllUsers().observe(this, users -> {
            adapter.submitList(users);
        });

        wireUpDisplay();
    }

    private void wireUpDisplay() {
        binding.MakeAdminButton.setOnClickListener(v -> makeAdmin());
        binding.DeleteUserButton.setOnClickListener(v -> deleteUser());
        binding.FinishManageUsersButton.setOnClickListener(v -> finishManaging());
    }

    private void makeAdmin() {
        currentMode = 1;
        binding.ManageUsersDescriptionTextview.setText(R.string.enter_a_username_to_make_admin);
        binding.ManageUsersDescriptionTextview.setVisibility(View.VISIBLE);
        binding.MakeAdminButton.setVisibility(View.INVISIBLE);
        binding.DeleteUserButton.setVisibility(View.INVISIBLE);
        binding.FinishManageUsersButton.setVisibility(View.VISIBLE);
        binding.EnterUsernameEditText.setVisibility(View.VISIBLE);
    }

    private void deleteUser() {
        currentMode = 2;
        binding.ManageUsersDescriptionTextview.setText(R.string.enter_a_username_to_delete);
        binding.ManageUsersDescriptionTextview.setVisibility(View.VISIBLE);
        binding.MakeAdminButton.setVisibility(View.INVISIBLE);
        binding.DeleteUserButton.setVisibility(View.INVISIBLE);
        binding.FinishManageUsersButton.setVisibility(View.VISIBLE);
        binding.EnterUsernameEditText.setVisibility(View.VISIBLE);
    }

    /** finishManaging
     * processes user input depending on the context (currentMode)
     * Checks if username string is empty -> returns to default Manage Users view
     * Checks if username exists in the database -> returns error message
     * If username is valid, calls method based on whether
     * the user is going to be made admin, or user is going to be deleted
     * Returns to default Manage Users view
     */
    private void finishManaging() {
        // Retrieves inputted username
        String enteredUsername = binding.EnterUsernameEditText.getText().toString();
        if (enteredUsername.isEmpty()){
            toaster("Username cannot be empty.");
            currentMode = 0;
            binding.ManageUsersDescriptionTextview.setVisibility(View.INVISIBLE);
            binding.MakeAdminButton.setVisibility(View.VISIBLE);
            binding.DeleteUserButton.setVisibility(View.VISIBLE);
            binding.FinishManageUsersButton.setVisibility(View.INVISIBLE);
            binding.EnterUsernameEditText.setVisibility(View.INVISIBLE);
        }else if (repository.getUserByUsername(enteredUsername)==null){
            toaster(enteredUsername + " does not exist. View above list for valid usernames.");
        } else {
            if (currentMode == 1) {
                makeUserAdmin(binding.EnterUsernameEditText.getText().toString());
            } else if (currentMode == 2) {
                deleteThisUser(binding.EnterUsernameEditText.getText().toString());
            }
            currentMode = 0;
            binding.ManageUsersDescriptionTextview.setVisibility(View.INVISIBLE);
            binding.MakeAdminButton.setVisibility(View.VISIBLE);
            binding.DeleteUserButton.setVisibility(View.VISIBLE);
            binding.FinishManageUsersButton.setVisibility(View.INVISIBLE);
            binding.EnterUsernameEditText.setVisibility(View.INVISIBLE);
        }
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, ManageUsersActivity.class);
        return intent;
    }

    /** makeUserAdmin
     * gives a user admin permissions
     * @param username is the username of the selected user
     */
    public void makeUserAdmin(String username) {
        // Checks if the current user is already an admin
        if(repository.getUserByUsername(username).isAdmin()){
            toaster(username + " is already an admin.");
        }else{
            // Retrieves the user object from the database by username
            User newAdmin = repository.getUserByUsername(username);
            // sets the user object's isAdmin field to true
            newAdmin.setAdmin(true);
            // updates the DB to reflect the user's isAdmin field
            repository.updateUser(newAdmin);
            toaster(username + " is now an admin.");
        }

    }

    /** deleteThisUser
     * deletes a user from the user database
     * @param username is the username of the selected user
     */
    public void deleteThisUser(String username) {
        // Checks if the user is an admin (admin users cannot be deleted)
        if(repository.getUserByUsername(username).isAdmin()){
            toaster("You cannot delete an admin user.");
        }else{
            // Retrieves the user object from the database by username
            User deleteUser = repository.getUserByUsername(username);
            // Deletes the user from the DB
            repository.deleteUser(deleteUser);
            toaster(username + " has been deleted from Spawtify.");
        }
    }

    /** toaster
     * takes some bread (a message) and makes toast
     * @param message the message displayed in the Toast
     */
    private void toaster(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}