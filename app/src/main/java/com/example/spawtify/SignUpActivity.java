package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.Playlist;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivitySignUpBinding;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    //  Initialized in: getUserDatabase
    //  Used in: wireUpDisplay to insert newUser to database
    UserDAO userDAO;

    //  Used to treat items on screen as objects that can be manipulated or return info
    ActivitySignUpBinding binding;

    //  Adding this in here just in case database hasn't been created for some reason
    //  Very defensive add, can't think of why database wouldn't be created yet
    private SpawtifyRepository spawtifyRepository;

    //  Place to store user input
    private String usernameString;
    private String passwordString;
    private String confirmPasswordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //  Initializes database if it hasn't been initialized
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //  Initializes userDAO for use in inserting newUser to database
        getDatabase();

        // Sets up signup button
        wireUpDisplay();
    }

    /** wireUpDisplay:
     * Sets up signUpButton with a setOnClickListener that signs up user and takes them back to
     * login activity if username and password meet requirements
     */
    private void wireUpDisplay(){
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sign Up");

        Button signUpButton = binding.buttonConfirmSignUp;

        signUpButton.setOnClickListener(v -> {
            //  Retrieve values from display
            getValuesFromDisplay();

            //  If checkRequirements returns true, values from display meet requirements
            //  Account can be created
            if (checkRequirements()){
                //  User object called newUser created with user defined username and password
                User newUser = new User(usernameString, passwordString);

                //  UserDAO inserts newUser into database
                userDAO.insert(newUser);

                // creates default playlist for new user
                User currUser = userDAO.getUserByUsername(usernameString);
                Playlist defaultPlaylist = new Playlist("Purrsonal Playlist",
                        "Favorite mewsic CATalog", currUser.getUserId());
                defaultPlaylist.setSongIdString("1\n2\n3\n4");
                spawtifyRepository.insertPlaylist(defaultPlaylist);

                //  Returns user to login activity
                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /** checkRequirements:
     * Checks if username is already in use
     * Checks if username is longer than 3 characters
     * Checks if password is longer than 3 characters
     * Checks if password and confirm password match
     *
     * @return True if username and password meet requirements | False if either fail requirements
     */
    private boolean checkRequirements(){
        if (userDAO.getUserByUsername(usernameString) != null){
            toaster("Username already in use");
            return false;
        }
        if (usernameString.length() < 3){
            toaster("Username too short, must be at least 3 characters");
            return false;
        }
        if (passwordString.length() < 3){
            toaster("Password too short, must be at least 3 characters");
            return false;
        }
        if (!passwordString.equals(confirmPasswordString)){
            toaster("Passwords do not match");
            return false;
        }
        return true;
    }

    /** getValuesFromDisplay:
     * Retrieves string values input into text fields on screen
     */
    private void getValuesFromDisplay(){
        usernameString = binding.editTextSignUpUsername.getText().toString();
        passwordString = binding.editTextSignUpPassword.getText().toString();
        confirmPasswordString = binding.editTextConfirmPassword.getText().toString();
    }

    /** getDatabase:
     * Initializes userDAO for use in inserting user into database
     */
    private void getDatabase() {
        userDAO = Room.databaseBuilder
                        (this, SpawtifyDatabase.class, SpawtifyDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    /** intentFactory:
     *
     * @param applicationContext    current state of application
     * @return  intent to be used in startActivity method called in login activity
     */
    public static Intent intentFactory(Context applicationContext) {
        Intent intent = new Intent(applicationContext, SignUpActivity.class);
        return intent;
    }

    /** toaster
     * takes some bread (a message) and makes toast
     * @param message the message displayed in the Toast
     */
    private void toaster(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}