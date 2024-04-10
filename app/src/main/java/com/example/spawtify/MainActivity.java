package com.example.spawtify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //  USER FIELDS
    private static final String USER_ID_KEY = "com.example.spawtify.userIdKey";
    private UserDAO userDao;
    private List<User> users;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(USER_ID_KEY, userId);
        return intent;
    }
}