package com.example.spawtify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //  USER FIELDS
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";
    private static final String PREFERENCES_KEY = "com.example.spawtify.preferencesKey";
    private UserDAO userDao;
    private List<User> users;
    private int userId = -1;
    private SharedPreferences preferences = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDatabase();
        checkForUser();
        addUserToPreferences(userId);
    }

    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        userId = -1;

                    }
                }
    }

    private void addUserToPreferences(int userId){
        if (preferences == null){
            getPrefs();
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(USER_ID_KEY, userId);
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
        SharedPreferences preferences = this.getSharedPreferences
                                                        (PREFERENCES_KEY, Context.MODE_PRIVATE);
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
        if (users.size() <= 0){
            User defaultUser = new User("cat123", "cat123");
            userDao.insert(defaultUser);
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