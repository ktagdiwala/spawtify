package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.spawtify.Database.SpawtifyDatabase;
import com.example.spawtify.Database.SpawtifyRepository;
import com.example.spawtify.Database.UserDAO;
import com.example.spawtify.Database.entities.User;
import com.example.spawtify.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private User user;
    private String usernameString;
    private String passwordString;

    ActivityLoginBinding binding;

    private SpawtifyRepository spawtifyRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        spawtifyRepository = SpawtifyRepository.getRepository(getApplication());
        wireUpDisplay();

    }

    private void wireUpDisplay(){
        Button loginButton = binding.buttonLogin;
        Button signUpButton = binding.buttonSignUp;

        loginButton.setOnClickListener(v -> {
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

        signUpButton.setOnClickListener(v -> {
            Intent intent = SignUpActivity.intentFactory
                                (getApplicationContext());
            startActivity(intent);
        });
    }

    private boolean validatePassword(){
        return user.getPassword().equals(passwordString);
    }

    private void getValuesFromDisplay(){
        usernameString = binding.editTextLoginUsername.getText().toString();
        passwordString = binding.editTextLoginPassword.getText().toString();
    }

    private boolean checkForUserInDatabase(){
        user = spawtifyRepository.getUserByUsername(usernameString);
        if (user == null){
            Toast.makeText(this, "No user " + usernameString + " found", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context, LoginActivity.class);

        return intent;
    }
}