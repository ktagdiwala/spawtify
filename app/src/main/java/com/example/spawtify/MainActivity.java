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
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//    public static final String TAG = "Spawtify";


    //  USER FIELDS
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.spawtify.preferencesKey";
    private UserDAO userDao;
    private User user;
    private List<User> users;
    private int userId = -1;
    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireUpDisplay();
        getDatabase();
        checkForUser();
        addUserToPreferences(userId);
        loginUser(userId);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

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

    private void wireUpDisplay(){
        //  SCREEN/PAGE FIELDS
        Button logoutButton = findViewById(R.id.buttonLogout);

        logoutButton.setOnClickListener(v -> logoutUser());
    }

    private void loginUser(int userId){
        user = userDao.getUserByUserId(userId);
        invalidateOptionsMenu();
    }


    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

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

    public void getDatabase(){
        userDao = Room.databaseBuilder(this, SpawtifyDatabase.class, SpawtifyDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    private void getPrefs(){
        preferences = this.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

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

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}