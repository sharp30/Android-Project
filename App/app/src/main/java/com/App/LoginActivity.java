package com.App;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    Button createAccountBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //todo: check if user is already logged - using shared preference
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initials
        loginBtn = findViewById(R.id.btn_login);
        createAccountBtn = findViewById(R.id.btn_create_account);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: check if login is ok!
                Intent next = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(next);
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(),CreateAccountActivity.class);
                startActivity(next);
            }
        });
    }
}