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

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    //allows us to read information from the display
    ActivityMainBinding binding;

    public static final String TAG = "K_J_Spawtify";

    //  USER FIELDS
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.spawtify.preferencesKey";
    private UserDAO userDao;
    private User user;
    private List<User> users;
    private int userId = -1;
    private SharedPreferences preferences = null;

    private SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //turns UI elements (inflates them) into objects to manipulate in java files
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());
        getDatabase();
        wireUpDisplay();
        checkForUser();
        addUserToPreferences(userId);
        loginUser(userId);
        invalidateOptionsMenu();

        //Determines admin button visibility
        if(user.isAdmin()){
            binding.AdminButton.setVisibility(View.VISIBLE);
        }else{
            binding.AdminButton.setVisibility(View.INVISIBLE);
        }
    }

    /** onCreateOptionsMenu:
     *
     * @param menu The options menu in which you place your items.
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    /** onPrepareOptionsMenu:
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     *
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if (user == null){
            checkForUser();
        }
        item.setTitle(user.getUsername());
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

        binding.buttonLogout.setOnClickListener(v -> logoutUser());

    }

    /** loginUser:
     *
     * @param userId
     */
    private void loginUser(int userId){
        user = userDao.getUserByUserId(userId);
        invalidateOptionsMenu();
    }

    /** logoutUser:
     *
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
     *
     * @param userId
     */
    private void addUserToPreferences(int userId){
        if (preferences == null){
            getPrefs();
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
        editor.apply();
    }

    private void clearUserFromPref(){
        addUserToPreferences(-1);
    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY, -1);
    }

    /** getDatabase:
     *
     */
    public void getDatabase(){
        userDao = Room.databaseBuilder(this, SpawtifyDatabase.class, SpawtifyDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    /** getPrefs:
     *
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

        if (preferences == null){
            getPrefs();
        }

        userId = preferences.getInt(USER_ID_KEY, -1);

        //  if userId is not equal to -1, then SharedPreferences shows someone is logged in
        if (userId != -1){
            return;
        }

        //  Create default user if user list is empty
        List<User> users = userDao.getAllUsers();
        if (users.isEmpty()){
            User defaultUser = new User("cat123", "cat123");
            User altUser = new User("123cat", "123cat");
            userDao.insert(defaultUser, altUser);
        }

        //  Enter login screen
        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
    }

    /** intentFactory:
     *
     * @param context
     * @param userId
     * @return
     */
    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}