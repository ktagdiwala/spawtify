package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    private UserDAO userDAO;
    private User user;
    private String usernameString;
    private String passwordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wireUpDisplay();
        getDatabase();

    }

    private void getDatabase() {
        userDAO = Room.databaseBuilder(this, SpawtifyDatabase.class, SpawtifyDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    private void wireUpDisplay(){
        username = findViewById(R.id.editTextLoginUsername);
        password = findViewById(R.id.editTextLoginPassword);
        Button button = findViewById(R.id.buttonLogin);

        button.setOnClickListener(v -> {
            getValuesFromDisplay();
            if (checkForUserInDatabase()){
                if (!validatePassword()){
                    Toast.makeText
                                    (LoginActivity.this,
                                    "Invalid password",
                                    Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = MainActivity.intentFactory
                                        (getApplicationContext(),user.getUserId());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validatePassword(){
        return user.getPassword().equals(passwordString);
    }

    private void getValuesFromDisplay(){
        usernameString = username.getText().toString();
        passwordString = password.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        user = userDAO.getUserByUsername(usernameString);
        if (user == null){
            Toast.makeText(this, "No user " + user + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginActivity.class);

        return intent;
    }
}