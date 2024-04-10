package com.example.spawtify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //  USER FIELDS
    private UserDAO userDao;
    private List<User> users;
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}