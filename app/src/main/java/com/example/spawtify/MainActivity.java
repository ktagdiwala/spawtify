package com.example.spawtify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivityMainBinding;

import java.util.List;

/** MainActivity:
 *  Landing Page for users when logged in.
 *  Displays 4 options for user (same options for admins with additional admin option) as buttons:
 *  1) Browse Songs -> takes user to application's song list
 *  2) My Playlists -> takes user to their list of created playlists
 *  3) Change Password -> takes user to screen where they can change their password
 *  4) Logout -> logs out user from application and returns them to login page
 *
 *  **ADMIN ONLY**
 *  5) Secret Admin Stuff -> takes admin to admin perks page
 *
 * @author James Mondragon
 * @since 04-12-2024
 */
public class MainActivity extends AppCompatActivity {

    //allows us to read information from the display
    ActivityMainBinding binding;

    //  Tag for use during log statements
    public static final String TAG = "K_J_Spawtify";

    //  Creates object userDao
    //  Initialized in getDatabase
    //  For use in loginUser
    private UserDAO userDao;

    //  USER FIELDS

    //  Used as a key to retrieve value (int: userId) from LoginActivity
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";

    //  Used as a key to retrieve value (SharedPreferences: preferences)
    private static final String PREFERENCES_KEY = "com.example.spawtify.preferencesKey";

    //  Creates a user object, initialized in loginUser
    private User user;

    //  Stores current user's ID, no user logged in = -1
    private int userId = -1;

    //  Stores SharedPreferences object that allows us to edit preference data
    private SharedPreferences preferences = null;

    //  Creates SpawtifyRepository object
    //  Initialized in onCreate
    private SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  turns UI elements (inflates them) into objects to manipulate in java files
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        /*
        Initializes SpawtifyRepository object

        SpawtifyRepository:
            Calls getRepository method from SpawtifyRepository
            getRepository ensures only one instance of repository exists at a time
            (Singleton design pattern)
            Statement equal to spawtifyRepository = new SpawtifyRepository(application)
            This is a constructor for SpawtifyRepository

        SpawtifyDatabase:
            The SpawtifyRepository constructor creates a SpawtifyDatabase object and initializes it.
            call to getDatabase from SpawtifyDatabase creates database if it hasn't been created yet
            (another Singleton design pattern)
            Adds default values to database on creation
         */
        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());

        //  Initializes userDAO object for use in interacting with database
        getDatabase();

        //  Sets up on-screen buttons with onClickListeners
        wireUpDisplay();

        //  Checks to see if a user is already logged in by checking userId
        //  If userId != -1, then a user is logged in
        checkForUser();

        //  Add userId to preferences data
        addUserToPreferences(userId);

        //  Logs user in
        loginUser(userId);

        //  Feels redundant since it is executed at end of loginUser method, but will leave for now
        invalidateOptionsMenu();

        //Determines admin button visibility
        if(user.isAdmin()){
            binding.AdminButton.setVisibility(View.VISIBLE);
        }else{
            binding.AdminButton.setVisibility(View.INVISIBLE);
        }
    }

    /** onCreateOptionsMenu:
     * Sets up options menu to be displayed and sets up items for manipulation
     *
     * @param menu The options menu in which you place your items.
     *             Freebie parameter given by application
     *             Method called by application when theme is set to have an actionBar
     *
     * @return true if successfully ran
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  turns UI elements (inflates them) into objects to manipulate
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    /** onPrepareOptionsMenu:
     * Prepares options menu by: setting our item as visible, set item display text, set up item
     * with an onClickListener
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     *
     * @return true: if user is logged in | false: if user is logged out
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        //  Allows us to utilize MenuItem methods for item: logoutMenuItem
        MenuItem item = menu.findItem(R.id.logoutMenuItem);

        //  Ensures item in menu bar is visible
        item.setVisible(true);

        //  If user not initialized, check for user is called to initialize logged in user
        if (user == null){
            checkForUser();
        }

        //  Sets item display text to the user's username
        item.setTitle(user.getUsername());

        //  Sets onClickListener for item, when pressed, call logoutUser method
        item.setOnMenuItemClickListener(item1 -> {
            logoutUser();
            return false;
        });
        return true;
    }

    /** wireUpDisplay:
     * Connects the buttons on the screen to their corresponding views
     */
    private void wireUpDisplay(){
        //  SCREEN/PAGE FIELDS

        //  Sets up logout button -> calls logoutUser method when pressed
        binding.buttonLogout.setOnClickListener(v -> logoutUser());

        //  Sets up change password button -> calls changePassword method when pressed
        binding.ChangePasswordButton.setOnClickListener(v -> changePassword());
    }

    private void changePassword(){
        Intent intent = ChangePassword.intentFactory(this,userId);
        startActivity(intent);
    }

    /** loginUser:
     * Retrieves user from database using userID
     * Initializes user in this activity to retrieved user
     *
     * @param userId user's userID
     */
    private void loginUser(int userId){
        user = userDao.getUserByUserId(userId);

        //  Refreshes options menu: current user's username is displayed now
        invalidateOptionsMenu();
    }

    /** logoutUser:
     *  Logs out user by asking displaying dialog with yes or no options to the question "Logout?"
     *  Yes pressed: User is cleared from intent by setting USER_ID_KEY to -1
     *      User is cleared from preferences by writing -1 to preferences data
     *      userId is set to -1
     *      checkForUser is called which will send us back to the login page
     *  No pressed: nothing happens
     */
    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(binding.buttonLogout.getText().toString() + "?");

        alertBuilder.setPositiveButton(getString(R.string.yes),
                (dialog, which) -> {
                    clearUserFromIntent();
                    clearUserFromPref();
                    userId = -1;
                    checkForUser();
                });

        alertBuilder.setNegativeButton(getString(R.string.no), (dialog, which) -> {

        });

        alertBuilder.create().show();
    }

    /** addUserToPreferences:
     * User ID is added to preferences. This adds the current user to preference data
     *
     * @param userId    user ID is sent in to set preferences
     */
    private void addUserToPreferences(int userId){
        //  If preferences = null, set up preference data by calling getPrefs
        if (preferences == null){
            getPrefs();
        }

        //  Create preference editor
        SharedPreferences.Editor editor = preferences.edit();
        //  Editor adds userId to preferences data
        editor.putInt(USER_ID_KEY, userId);
        //  Editor saves/applies changes to preferences data
        editor.apply();
    }

    /** ClearUserFromPref:
     *  Clears user from preference data by setting preference data to -1
     */
    private void clearUserFromPref(){
        addUserToPreferences(-1);
    }

    /** ClearUserFromIntent:
     * Clears user ID from intent by writing USER_ID_KEY to -1
     */
    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    /** getDatabase:
     *  Initializes userDao object
     *  .build() creates instance of SpawtifyDatabase
     *  this allows the use of .getUserDAO which comes from SpawtifyDatabase
     */
    public void getDatabase(){
        userDao = Room.databaseBuilder(this, SpawtifyDatabase.class, SpawtifyDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    /** getPrefs:
     *  Gets preference data by calling current activity's method getSharedPreferences
     *  Key: PREFERENCES_KEY
     *  Value: Context.MODE_PRIVATE
     */
    private void getPrefs(){
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    /** checkForUser:
     * checks if a user is logged in and displays the login page or landing page accordingly
     */
    private void checkForUser() {
        userId = getIntent().getIntExtra(USER_ID_KEY, -1);

        //  If userId is not equal to -1, then someone has logged in and stored their USER_ID_KEY
        //  to the current intent
        if (userId != -1){
            return;
        }

        //  If preferences have not been setup, call getPrefs to get current preference data
        if (preferences == null){
            getPrefs();
        }

        //  Get user ID from preference data
        userId = preferences.getInt(USER_ID_KEY, -1);

        //  if userId is not equal to -1, then SharedPreferences shows someone is logged in
        if (userId != -1){
            return;
        }

        //  Enter login screen
        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    /** intentFactory:
     *
     * @param context   current state of application
     * @param userId    userId received from login activity
     * @return  intent to be used in startActivity method called in login activity
     */
    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}