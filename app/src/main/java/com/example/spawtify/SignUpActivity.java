package com.example.spawtify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


    }

    public static Intent intentFactory(Context applicationContext) {
        Intent intent = new Intent(applicationContext, SignUpActivity.class);

        return intent;
    }
}